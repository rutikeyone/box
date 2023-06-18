package com.box.data.repository

import android.accounts.Account
import com.box.domain.entity.AccountEntity

interface AccountsRepository {
    suspend fun getAccount(): AccountEntity?
}