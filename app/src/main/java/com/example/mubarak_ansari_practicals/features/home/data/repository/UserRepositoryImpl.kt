package com.example.mubarak_ansari_practicals.features.home.data.repository

import com.example.mubarak_ansari_practicals.di.network.ApiService
import com.example.mubarak_ansari_practicals.features.home.data.model.UserPageResponse
import com.example.mubarak_ansari_practicals.features.home.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApiService) : UserRepository {
    override suspend fun getUserListing(sort:String): UserPageResponse {
        return apiService.getUsersData(sort = sort)
    }
}