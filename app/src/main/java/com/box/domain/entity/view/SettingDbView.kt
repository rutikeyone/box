package com.box.domain.entity.view

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Embedded
import com.box.domain.entity.room.SettingsTuple

@DatabaseView(
    viewName = "settings_view",
    value = "SELECT \n" +
            "   accounts.id as account_id, \n" +
            " boxes.id  as box_id, \n" +
            " ifnull(accounts_boxes_settings.is_active, 1) as is_active\n" +
            "FROM accounts\n" +
            "JOIN boxes\n" +
            "LEFT JOIN accounts_boxes_settings\n" +
            " ON accounts_boxes_settings.account_id = accounts.id \n" +
            " AND accounts_boxes_settings.box_id = boxes.id"
)
data class SettingDbView(
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "box_id") val boxId: Long,
    @Embedded val settings: SettingsTuple,
)