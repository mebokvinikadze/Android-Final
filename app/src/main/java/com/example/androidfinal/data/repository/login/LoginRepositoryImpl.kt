package com.example.androidfinal.data.repository.login

import com.example.androidfinal.common.FirebaseHelper
import com.example.androidfinal.common.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseHelper: FirebaseHelper
) : LoginRepository {
    override suspend fun loginUser(email: String, password: String): Resource<Unit> {
        return firebaseHelper.handleFirebaseRequest {
            auth.signInWithEmailAndPassword(email, password).await()
        }
    }

}