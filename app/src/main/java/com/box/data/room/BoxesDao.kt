package com.box.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.box.domain.entity.room.AccountBoxSettingDbEntity
import com.box.domain.entity.room.SettingsWithEntitiesTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface BoxesDao {
    @Transaction
    @Query("SELECT * FROM settings_view WHERE account_id = :accountId")
    fun getBoxesAndSettings(accountId: Long): Flow<List<SettingsWithEntitiesTuple>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setActiveFlagForBox(accountBoxSetting: AccountBoxSettingDbEntity)
}