package com.box.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.box.domain.entity.room.AccountAndAllSettingsTuple
import com.box.domain.entity.room.AccountAndEditedBoxesTuple
import com.box.domain.entity.room.AccountDbEntity
import com.box.domain.entity.room.AccountSignInTuple
import com.box.domain.entity.room.AccountUpdateUsernameTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountsDao {
    @Query("SELECT id, hash, salt FROM accounts WHERE email = :email")
    suspend fun findByEmail(email: String): AccountSignInTuple?

    @Update(entity = AccountDbEntity::class)
    suspend fun updateUsername(accountUpdateUsernameTuple: AccountUpdateUsernameTuple)

    @Insert
    suspend fun createAccount(accountDbEntity: AccountDbEntity)

    @Query("SELECT * FROM accounts WHERE id=:accountId")
    fun getById(accountId: Long): Flow<AccountDbEntity?>

    @Transaction
    @Query("SELECT * FROM accounts WHERE accounts.id = :accountId")
    fun getAccountAndEditedBoxes(accountId: Long): AccountAndEditedBoxesTuple

    @Transaction
    @Query("SELECT * FROM accounts")
    fun getAllData(): Flow<List<AccountAndAllSettingsTuple>>
}