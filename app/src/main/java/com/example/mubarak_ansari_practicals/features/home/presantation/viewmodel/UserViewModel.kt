package com.example.mubarak_ansari_practicals.features.home.presantation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mubarak_ansari_practicals.features.home.data.model.Item
import com.example.mubarak_ansari_practicals.features.home.domain.usecase.UserListUseCase
import com.example.mubarak_ansari_practicals.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val useCase: UserListUseCase
) : ViewModel() {

    private val _userListState = MutableStateFlow<UserListState>(UserListState.Loading)
    val userListState: StateFlow<UserListState> = _userListState

    var sortFilter = "name"

    fun getUserListing() {
        useCase.invoke(sortFilter).onEach { result ->
            when (result) {
                is NetworkResponse.Loading -> {
                    Log.e("TAG", "getUserListing: Loading...")
                    _userListState.value = UserListState.Loading
                }

                is NetworkResponse.Error -> {
                    Log.e("TAG", "getUserListing: Error... ${result.errorMessage}")
                    _userListState.value = UserListState.Error
                }

                is NetworkResponse.Success -> {
                    Log.e("TAG", "getUserListing: Success... => ${result.result}")
                    result.result?.items?.let {
                        _userListState.value = UserListState.Success(it)
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    fun reset() {
        _userListState.value = UserListState.Loading
    }

}

sealed class UserListState {
    object None : UserListState()
    object Loading : UserListState()
    object Error : UserListState()
    data class Success(val response: List<Item>) : UserListState()
}