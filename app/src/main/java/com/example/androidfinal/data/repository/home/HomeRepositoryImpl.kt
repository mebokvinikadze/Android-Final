package com.example.androidfinal.data.repository.home

import com.example.androidfinal.data.repository.datastore.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl@Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : HomeRepository{
    override suspend fun getUserWorkerCountFromDataStore(): Flow<Int?> {
        return dataStoreRepository.workoutCount
    }
}