package com.example.androidfinal.data.repository.home

import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getUserWorkerCountFromDataStore(): Flow<Int?>
}