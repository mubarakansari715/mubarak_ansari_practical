package com.example.mubarak_ansari_practicals.utils

sealed class NetworkResponse<out T : Any?> {
    object Loading : NetworkResponse<Nothing>()
    data class Error(val errorMessage: String) : NetworkResponse<Nothing>()
    data class Success<out T : Any>(val result: T?) : NetworkResponse<T>()
}