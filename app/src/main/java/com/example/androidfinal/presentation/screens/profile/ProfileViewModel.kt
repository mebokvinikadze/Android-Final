package com.example.androidfinal.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidfinal.common.Resource
import com.example.androidfinal.data.repository.datastore.DataStoreRepository
import com.example.androidfinal.data.repository.profile.ProfileRepository
import com.example.androidfinal.presentation.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _profileState = MutableStateFlow<Resource<Unit>>(Resource.Loading)
    val profileState: StateFlow<Resource<Unit>> = _profileState

    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> = _userState

    init {
        getUserData()
    }

    private fun getUserData() {
        viewModelScope.launch {
            val username = dataStoreRepository.username.firstOrNull() ?: ""
            val age = dataStoreRepository.userAge.firstOrNull() ?: 0
            val weight = dataStoreRepository.userWeight.firstOrNull() ?: 0
            val height = dataStoreRepository.userHeight.firstOrNull() ?: 0

            _userState.value = User(username, age, weight, height)
        }
    }


    fun updateProfile(username: String, age: Int, weight: Int, height: Int) {
        viewModelScope.launch {
            _profileState.value = Resource.Loading

            val result = profileRepository.updateUserProfile(username, age, weight, height)

            _profileState.value = result
        }
    }


    suspend fun logOut() {
        dataStoreRepository.clearLoginInfo()
    }


    fun updateLanguage(language: String) {
        viewModelScope.launch {
            dataStoreRepository.saveLanguagePreference(language)
            dataStoreRepository.applySavedLanguage()
        }
    }

}