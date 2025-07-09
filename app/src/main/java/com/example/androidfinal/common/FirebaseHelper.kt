package com.example.androidfinal.common

import android.content.Context
import com.example.androidfinal.R
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestoreException
import java.io.IOException

class FirebaseHelper(private val context: Context) {

    suspend fun <T> handleFirebaseRequest(apiCall: suspend () -> T): Resource<T> {
        if (!NetworkHelper.isNetworkAvailable(context)) {
            return Resource.Error(errorMessage = context.getString(R.string.no_internet_connection))
        }

        return try {
            val result = apiCall.invoke()
            Resource.Success(result)
        } catch (throwable: Throwable) {
            when (throwable) {
                is FirebaseAuthUserCollisionException -> Resource.Error(errorMessage = throwable.message ?: "This email is already in use. Try logging in")
                is FirebaseAuthException -> Resource.Error(errorMessage = throwable.message ?: "Auth Exception")
                is FirebaseFirestoreException -> Resource.Error(errorMessage =  throwable.message ?: "FireStore Exception")
                is IOException -> Resource.Error(errorMessage = throwable.message ?: "IO Exception")
                else -> Resource.Error(errorMessage = throwable.message ?: "Unknown Exception")
            }
        }
    }
}
