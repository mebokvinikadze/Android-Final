package com.example.androidfinal.presentation.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidfinal.common.Resource
import com.example.androidfinal.data.repository.register.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : ViewModel() {

    private val _registerState = MutableStateFlow<Resource<Unit>>(Resource.Loading)
    val registerState: StateFlow<Resource<Unit>> = _registerState

    fun register(
        email: String,
        password: String,
        username: String,
        weight: Int,
        age: Int,
        height: Int
    ) {
        viewModelScope.launch {
            _registerState.value = Resource.Loading

            val result =
                registerRepository.registerUser(email, password, username, weight, age, height)

            _registerState.value = result
        }
    }


}