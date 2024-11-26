package com.example.mubarak_ansari_practicals.di.modules

import com.example.mubarak_ansari_practicals.di.network.ApiService
import com.example.mubarak_ansari_practicals.features.home.data.repository.UserRepositoryImpl
import com.example.mubarak_ansari_practicals.features.home.domain.repository.UserRepository
import com.example.mubarak_ansari_practicals.features.home.domain.usecase.UserListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserListModule {

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService): UserRepository = UserRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideUseCase(repository: UserRepository) :UserListUseCase = UserListUseCase(repository)
}