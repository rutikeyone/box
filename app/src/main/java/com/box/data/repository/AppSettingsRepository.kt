package com.box.data.repository

import android.util.Log

interface AppSettingsRepository {

    fun getCurrentAccountId(): Long

    fun setCurrentAccountId(accountId: Long)

    companion object {
        const val NO_ACCOUNT_ID = -1L
    }
}