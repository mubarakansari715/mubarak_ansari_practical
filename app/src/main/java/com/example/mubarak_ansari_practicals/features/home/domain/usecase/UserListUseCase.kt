package com.example.mubarak_ansari_practicals.features.home.domain.usecase

import com.example.mubarak_ansari_practicals.features.home.data.model.UserPageResponse
import com.example.mubarak_ansari_practicals.features.home.domain.repository.UserRepository
import com.example.mubarak_ansari_practicals.utils.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserListUseCase @Inject constructor(private val repository: UserRepository) {

    fun invoke(sort: String): Flow<NetworkResponse<UserPageResponse>> = flow {
        try {
            emit(NetworkResponse.Loading)
            val response = repository.getUserListing(sort)
            emit(NetworkResponse.Success(response))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }
}