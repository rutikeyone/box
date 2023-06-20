package com.box.data.repository

import com.box.domain.entity.AccountEntity
import com.box.domain.entity.AccountFullDataEntity
import com.box.domain.entity.SignUpDataEntity
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {
    suspend fun isSignedIn(): Boolean
    suspend fun signIn(email: String, password: CharArray)
    suspend fun signUp(signUpDataEntity: SignUpDataEntity)
    suspend fun logout()
    suspend fun getAccount(): Flow<AccountEntity?>
    suspend fun updateAccountUsername(newUsername: String)
    suspend fun getAllData(): Flow<List<AccountFullDataEntity>>
}