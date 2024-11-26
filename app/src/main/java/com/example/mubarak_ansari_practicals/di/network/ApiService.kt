package com.example.mubarak_ansari_practicals.di.network

import com.example.mubarak_ansari_practicals.features.home.data.model.UserPageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUsersData(
        @Query("page") page: Int? = 1,
        @Query("pagesize") pagesize: Int? = 10,
        @Query("site") site: String? = "stackoverflow",
        @Query("sort") sort: String? = "name"
    ): UserPageResponse
}