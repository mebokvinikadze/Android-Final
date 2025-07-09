package com.example.androidfinal.data.repository.datastore

import kotlinx.coroutines.flow.Flow

interface IDataStoreRepository {
    val userId: Flow<String?>
    val username: Flow<String?>
    val userAge: Flow<Int?>
    val userWeight: Flow<Int?>
    val userHeight: Flow<Int?>
    val workoutCount: Flow<Int?>
    val rememberMe: Flow<Boolean>

    suspend fun saveUserInfo(
        uid: String,
        username: String,
        age: Int,
        weight: Int,
        height: Int,
        workoutCount: Int,
        rememberMe: Boolean
    )

    suspend fun updateUserDetails(username: String, age: Int, weight: Int, height: Int)

    suspend fun clearLoginInfo()

    suspend fun updateWorkoutCount(count: Int)

    suspend fun saveLanguagePreference(language: String)

    suspend fun applySavedLanguage()
}
