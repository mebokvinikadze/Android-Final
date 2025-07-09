package com.example.androidfinal.data.repository.register


import com.example.androidfinal.common.FirebaseHelper
import com.example.androidfinal.common.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class RegisterRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val firebaseHelper: FirebaseHelper
) : RegisterRepository {

    override suspend fun registerUser(
        email: String,
        password: String,
        username: String,
        weight: Int,
        age: Int,
        height: Int
    ): Resource<Unit> {
        return firebaseHelper.handleFirebaseRequest {
            val user = auth.createUserWithEmailAndPassword(email, password).await().user
            user?.let {
                val userData = mapOf("username" to username,  "weight" to weight, "age" to age, "height" to height)
                db.collection("users").document(user.uid).set(userData).await()
            }
        }
    }
}
