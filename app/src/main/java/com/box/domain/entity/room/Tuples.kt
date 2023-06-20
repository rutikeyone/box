package com.box.domain.entity.room

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.box.domain.entity.view.SettingDbView

data class AccountSignInTuple(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "hash") val hash: String,
    @ColumnInfo(name = "salt") val salt: String,
)

data class AccountUpdateUsernameTuple(
    @ColumnInfo(name = "id") @PrimaryKey val id: Long,
    @ColumnInfo(name = "username") val username: String,
)

data class AccountAndEditedBoxesTuple(
    @Embedded val accountDbEntity: AccountDbEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = AccountBoxSettingDbEntity::class,
            parentColumn = "account_id",
            entityColumn = "box_id"
        )
    )
    val boxes: List<BoxDbEntity>
)

data class AccountAndAllSettingsTuple(
    @Embedded val accountDbEntity: AccountDbEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "account_id",
        entity = SettingDbView::class
    )
    val settings: List<SettingAndBoxTuple>
)

data class SettingAndBoxTuple(
    @Embedded val accountBoxSettingsDbEntity: SettingDbView,
    @Relation(
        parentColumn = "box_id",
        entityColumn = "id"
    )
    val boxDbEntity: BoxDbEntity,
)

data class SettingsTuple(
    @ColumnInfo(name = "is_active") val isActive: Boolean,
)

data class BoxAndSettingsTuple(
    @Embedded val boxDbEntity: BoxDbEntity,
    @Embedded val settingDbEntity: AccountBoxSettingDbEntity?
)

data class SettingsWithEntitiesTuple(
    @Embedded val settingDbEntity: SettingDbView,
    @Relation(
        parentColumn = "account_id",
        entityColumn = "id"
    )
    val accountDbEntity: AccountDbEntity,

    @Relation(
        parentColumn = "box_id",
        entityColumn = "id"
    )
    val boxDbEntity: BoxDbEntity
)