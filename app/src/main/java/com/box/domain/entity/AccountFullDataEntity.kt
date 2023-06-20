package com.box.domain.entity

data class AccountFullDataEntity(
    val account: AccountEntity,
    val boxesAndSettings: List<BoxAndSettingsEntity>,
)