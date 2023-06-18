package com.box.domain.repository

import android.accounts.Account
import com.box.data.repository.AccountsRepository
import com.box.domain.entity.AccountEntity
import kotlinx.coroutines.delay

class RoomAccountsRepository: AccountsRepository {
    override suspend fun getAccount(): AccountEntity? {
        delay(1000)
        return null
    }
}