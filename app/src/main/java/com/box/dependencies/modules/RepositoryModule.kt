package com.box.dependencies.modules

import com.box.data.repository.AccountsRepository
import com.box.data.repository.AppSettingsRepository
import com.box.data.repository.BoxesRepository
import com.box.data.repository.SecureUtilsRepository
import com.box.domain.repository.DefaultSecureUtilsRepository
import com.box.domain.repository.RoomAccountsRepository
import com.box.domain.repository.RoomBoxesRepository
import com.box.domain.repository.SharedPreferenceAppSettingsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAppSettingsRepository(repository: SharedPreferenceAppSettingsRepository): AppSettingsRepository

    @Binds
    @Singleton
    abstract fun bindSecureUtilsRepository(repository: DefaultSecureUtilsRepository): SecureUtilsRepository

    @Binds
    @Singleton
    abstract fun bindAccountsRepository(repository: RoomAccountsRepository): AccountsRepository

    @Binds
    @Singleton
    abstract fun bindBoxesRepository(repository: RoomBoxesRepository): BoxesRepository
}

@Module
@InstallIn(SingletonComponent::class)
class RepositoryUtilsModule {
    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}