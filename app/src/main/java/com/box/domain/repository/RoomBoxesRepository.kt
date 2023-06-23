package com.box.domain.repository

import com.box.data.repository.AccountsRepository
import com.box.data.repository.BoxesRepository
import com.box.data.room.AppDatabase
import com.box.domain.entity.BoxAndSettingsEntity
import com.box.domain.entity.BoxEntity
import com.box.domain.entity.room.AccountBoxSettingDbEntity
import com.box.domain.entity.room.AuthException
import com.box.domain.entity.room.SettingsTuple
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class RoomBoxesRepository @Inject constructor(
    private val appDatabase: AppDatabase,
    private val ioDispatcher: CoroutineDispatcher,
    private val accountsRepository: AccountsRepository,
): BoxesRepository {
    override suspend fun getBoxesAndSettings(onlyActive: Boolean): Flow<List<BoxAndSettingsEntity>> {
        return accountsRepository.getAccount()
            .flatMapLatest { account ->
                if (account == null) return@flatMapLatest flowOf(emptyList())
                queryBoxesAndSettings(account.id)
            }
            .mapLatest { boxAndSettings ->
                if (onlyActive) {
                    boxAndSettings.filter { it.isActive }
                } else {
                    boxAndSettings
                }
            }
    }

    override suspend fun activateBox(box: BoxEntity) {
        setActiveFragForBox(box, true)
    }

    override suspend fun deactivateBox(box: BoxEntity) {
        setActiveFragForBox(box, false)
    }

    private fun queryBoxesAndSettings(accountId: Long): Flow<List<BoxAndSettingsEntity>> {
        return appDatabase.getBoxesDao().getBoxesAndSettings(accountId)
            .map { entities ->
                entities.map {
                    val boxEntity = it.boxDbEntity
                    val settingEntity = it.settingDbEntity
                    BoxAndSettingsEntity(
                        box = boxEntity.toBox(),
                        isActive = settingEntity.settings.isActive
                    )
                }
            }
    }

    private suspend fun setActiveFragForBox(box: BoxEntity, isActive: Boolean) {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        appDatabase.getBoxesDao().setActiveFlagForBox(
            AccountBoxSettingDbEntity(
                accountId = account.id,
                boxId = box.id,
                settings = SettingsTuple(isActive)
            )
        )
    }
}