package com.example.androidfinal.data.repository.login

import com.example.androidfinal.common.Resource

interface LoginRepository {
    suspend fun loginUser(email: String, password: String): Resource<Unit>
}