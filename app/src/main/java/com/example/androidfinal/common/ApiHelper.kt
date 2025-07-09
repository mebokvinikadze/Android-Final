package com.example.androidfinal.common


import android.content.Context
import com.example.androidfinal.R
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ApiHelper(private val context: Context) {


    suspend fun <T> handleHttpRequest(apiCall: suspend () -> Response<T>): Resource<T> {
        if (!NetworkHelper.isNetworkAvailable(context)) {
            return Resource.Error(errorMessage = context.getString(R.string.no_internet_connection))
        }

        val response = apiCall.invoke()
        return try {
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(data = it)
                } ?: Resource.Error(errorMessage = "Something went wrong")
            } else {
                Resource.Error(errorMessage = response.errorBody()?.string() ?: "Error")
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    Resource.Error(errorMessage = throwable.message ?: "IO Exception")
                }

                is HttpException -> {
                    Resource.Error(errorMessage = throwable.message ?: "Http Exception")
                }

                is IllegalStateException -> {
                    Resource.Error(errorMessage = throwable.message ?: "Illegal State Exception")
                }

                else -> {
                    Resource.Error(errorMessage = throwable.message ?: "Unknown Exception")
                }
            }

        }
    }
}