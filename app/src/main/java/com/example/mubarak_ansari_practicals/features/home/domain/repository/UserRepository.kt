package com.example.mubarak_ansari_practicals.features.home.domain.repository

import com.example.mubarak_ansari_practicals.features.home.data.model.UserPageResponse

interface UserRepository {

    suspend fun getUserListing(sort: String): UserPageResponse
}