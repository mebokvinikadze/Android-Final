package com.example.androidfinal.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidfinal.common.Resource
import com.example.androidfinal.data.repository.datastore.DataStoreRepository
import com.example.androidfinal.data.repository.login.LoginRepository
import com.example.androidfinal.presentation.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val firebaseFireStore: FirebaseFirestore
) : ViewModel() {

    private val _loginState = MutableStateFlow<Resource<Unit>>(Resource.Loading)
    val loginState: StateFlow<Resource<Unit>> = _loginState

    init {
        applyLanguage()
    }

    suspend fun checkSavedUser(): Boolean {
        if (!dataStoreRepository.rememberMe.first()) {
            dataStoreRepository.clearLoginInfo()
            return false
        }
        val token = dataStoreRepository.userId.first()
        return !token.isNullOrEmpty()
    }


    fun login(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {
            _loginState.value = Resource.Loading

            val result = loginRepository.loginUser(email, password)

            if (result is Resource.Success) {
                val user = FirebaseAuth.getInstance().currentUser

                user?.uid?.let { uid ->
                    val snapshot = firebaseFireStore.collection("users").document(uid).get().await()

                    val userData = snapshot.toObject(User::class.java)
                    val workoutCount = snapshot.getLong("workout_count")?.toInt() ?: 0

                    userData?.let { data ->
                        dataStoreRepository.saveUserInfo(
                            uid,
                            data.username,
                            data.age,
                            data.weight,
                            data.height,
                            workoutCount,
                            rememberMe
                        )
                    }
                }
            }

            _loginState.value = result
        }
    }

    private fun applyLanguage() {
        viewModelScope.launch {
            dataStoreRepository.applySavedLanguage()
        }
    }
}