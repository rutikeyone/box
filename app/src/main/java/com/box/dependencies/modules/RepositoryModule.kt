package com.box.dependencies.modules

import com.box.data.repository.AppSettingsRepository
import com.box.data.repository.SecureUtilsRepository
import com.box.domain.repository.DefaultSecureUtilsRepository
import com.box.domain.repository.SharedPreferenceAppSettingsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAppSettingsRepository(repository: SharedPreferenceAppSettingsRepository): AppSettingsRepository

    @Binds
    abstract fun bindSecureUtilsRepository(repository: DefaultSecureUtilsRepository): SecureUtilsRepository
}

@Module
@InstallIn(SingletonComponent::class)
class RepositoryStuffModule {
    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}