package com.box.domain.repository

import android.content.Context
import com.box.data.repository.AppSettingsRepository
import com.box.data.repository.AppSettingsRepository.Companion.NO_ACCOUNT_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferenceAppSettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context,
): AppSettingsRepository {
    private val sharedPreferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)

    override fun getCurrentAccountId(): Long = sharedPreferences.getLong(PREF_CURRENT_ACCOUNT_ID, NO_ACCOUNT_ID)

    override fun setCurrentAccountId(accountId: Long) {
        sharedPreferences.edit()
            .putLong(PREF_CURRENT_ACCOUNT_ID, accountId)
            .apply()
    }

    companion object {
        private const val SETTINGS = "settings"
        private const val PREF_CURRENT_ACCOUNT_ID = "currentAccountId"
    }
}