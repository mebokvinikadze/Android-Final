package com.example.androidfinal.data.repository.profile

import com.example.androidfinal.common.Resource

interface ProfileRepository {
    suspend fun updateUserProfile(username: String, age: Int, weight: Int, height: Int): Resource<Unit>
}
