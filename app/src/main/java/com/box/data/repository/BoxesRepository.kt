package com.box.data.repository

import com.box.domain.entity.BoxAndSettingsEntity
import com.box.domain.entity.BoxEntity
import kotlinx.coroutines.flow.Flow

interface BoxesRepository {
    suspend fun getBoxesAndSettings(onlyActive: Boolean = false): Flow<List<BoxAndSettingsEntity>>
    suspend fun activateBox(box: BoxEntity)
    suspend fun deactivateBox(box: BoxEntity)
}