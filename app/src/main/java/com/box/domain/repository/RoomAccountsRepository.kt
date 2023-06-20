package com.box.domain.repository

import android.database.sqlite.SQLiteConstraintException
import com.box.data.room.AccountsDao
import com.box.data.repository.AccountsRepository
import com.box.data.repository.AppSettingsRepository
import com.box.data.repository.SecureUtilsRepository
import com.box.data.room.AppDatabase
import com.box.domain.entity.AccountEntity
import com.box.domain.entity.AccountFullDataEntity
import com.box.domain.entity.BoxAndSettingsEntity
import com.box.domain.entity.SignUpDataEntity
import com.box.domain.entity.room.AccountDbEntity
import com.box.domain.entity.room.AccountUpdateUsernameTuple
import com.box.domain.entity.room.AuthException
import com.box.domain.entity.room.StorageException
import com.box.domain.entity.room.utils.AsyncLoader
import com.box.domain.entity.room.utils.wrapSQLiteException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomAccountsRepository @Inject constructor(
    private val appDatabase: AppDatabase,
    private val appSettingsRepository: AppSettingsRepository,
    private val secureUtilsRepository: SecureUtilsRepository,
    private val ioDispatcher: CoroutineDispatcher,
) : AccountsRepository {

    private val currentAccountIdFlow = AsyncLoader {
        MutableStateFlow(AccountId(appSettingsRepository.getCurrentAccountId()))
    }
    override suspend fun isSignedIn(): Boolean {
        delay(2000)
        return appSettingsRepository.getCurrentAccountId() != AppSettingsRepository.NO_ACCOUNT_ID
    }

    override suspend fun signIn(email: String, password: CharArray) = wrapSQLiteException(ioDispatcher){
        delay(1000)
        val accountId = findAccountIdByEmailAndPassword(email, password)
        appSettingsRepository.setCurrentAccountId(accountId)
        currentAccountIdFlow.get().value = AccountId(accountId)
        return@wrapSQLiteException
    }

    override suspend fun signUp(signUpDataEntity: SignUpDataEntity) = wrapSQLiteException(ioDispatcher){
        delay(1000)
        createAccount(signUpDataEntity)
    }

    override suspend fun logout() {
        appSettingsRepository.setCurrentAccountId(AppSettingsRepository.NO_ACCOUNT_ID)
        currentAccountIdFlow.get().value = AccountId(AppSettingsRepository.NO_ACCOUNT_ID)
    }
    override suspend fun getAccount(): Flow<AccountEntity?> {
        return currentAccountIdFlow.get()
            .flatMapLatest { accountId ->
                if(accountId.value == AppSettingsRepository.NO_ACCOUNT_ID) {
                    flowOf(null)
                } else {
                    getAccountById(accountId.value)
                }
            }
            .flowOn(ioDispatcher)
    }
    override suspend fun updateAccountUsername(newUsername: String) = wrapSQLiteException(ioDispatcher){
        delay(1000)
        val accountId = appSettingsRepository.getCurrentAccountId()
        if(accountId == AppSettingsRepository.NO_ACCOUNT_ID) throw AuthException()

        updateUsernameForAccountId(accountId, newUsername)
        currentAccountIdFlow.get().value = AccountId(accountId)
        return@wrapSQLiteException
    }

    override suspend fun getAllData(): Flow<List<AccountFullDataEntity>> {
        val account = getAccount().first()
        if(account == null || !account.isAdmin()) throw AuthException()
        return appDatabase.getAccountsDao().getAllData().map {accountAndSettings ->
            accountAndSettings.map {accountAndSettingsTuple ->
                AccountFullDataEntity(
                    account = accountAndSettingsTuple.accountDbEntity.toAccount(),
                    boxesAndSettings = accountAndSettingsTuple.settings.map {
                        BoxAndSettingsEntity(
                            box = it.boxDbEntity.toBox(),
                            isActive = it.accountBoxSettingsDbEntity.settings.isActive,
                        )
                    }
                )
            }
        }
    }

    private suspend fun findAccountIdByEmailAndPassword(email: String, password: CharArray): Long {
        val tuple = appDatabase.getAccountsDao().findByEmail(email) ?: throw AuthException()
        val saltBytes = secureUtilsRepository.stringToBytes(tuple.salt)
        val hashBytes =  secureUtilsRepository.passwordToHash(password, saltBytes)
        val hashString = secureUtilsRepository.bytesToString(hashBytes)
        password.fill('*')
        if(tuple.hash !=  hashString) throw AuthException()
        return tuple.id
    }

    private suspend fun createAccount(signUpDataEntity: SignUpDataEntity) {
        try {
            val entity = AccountDbEntity.fromSignUpData(signUpDataEntity, secureUtilsRepository)
            appDatabase.getAccountsDao().createAccount(entity)
        } catch (e: SQLiteConstraintException) {
            val exception = StorageException()
            exception.initCause(e)
            throw exception
        }
    }

    private fun getAccountById(accountId: Long): Flow<AccountEntity?> {
        return appDatabase.getAccountsDao().getById(accountId).map { it?.toAccount() }
    }

    private suspend fun updateUsernameForAccountId(accountId: Long, newUsername: String) {
        appDatabase.getAccountsDao().updateUsername(AccountUpdateUsernameTuple(accountId, newUsername))
    }

    private class AccountId(val value: Long)
}