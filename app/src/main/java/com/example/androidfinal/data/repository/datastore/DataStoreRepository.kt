package com.example.androidfinal.data.repository.datastore

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DataStoreRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : IDataStoreRepository {

    companion object {
        val REMEMBER_ME = booleanPreferencesKey("remember_me")
        val USER_ID = stringPreferencesKey("user_id")
        val USERNAME = stringPreferencesKey("username")
        val USER_AGE = intPreferencesKey("user_age")
        val USER_WEIGHT = intPreferencesKey("user_weight")
        val USER_HEIGHT = intPreferencesKey("user_height")
        val WORKOUT_COUNT = intPreferencesKey("workout_count")

        val LANGUAGE = stringPreferencesKey("language")


    }

    override val userId: Flow<String?> = dataStore.data.map { it[USER_ID] }
    override val username: Flow<String?> = dataStore.data.map { it[USERNAME] }
    override val userAge: Flow<Int?> = dataStore.data.map { it[USER_AGE] }
    override val userWeight: Flow<Int?> = dataStore.data.map { it[USER_WEIGHT] }
    override val userHeight: Flow<Int?> = dataStore.data.map { it[USER_HEIGHT] }
    override val workoutCount: Flow<Int?> = dataStore.data.map { it[WORKOUT_COUNT] }
    override val rememberMe: Flow<Boolean> = dataStore.data.map { it[REMEMBER_ME] ?: false }

    override suspend fun saveUserInfo(
        uid: String,
        username: String,
        age: Int,
        weight: Int,
        height: Int,
        workoutCount :Int,
        rememberMe: Boolean
    ) {
        dataStore.edit { preferences ->
            preferences[USER_ID] = uid
            preferences[USER_AGE] = age
            preferences[USERNAME] = username
            preferences[USER_WEIGHT] = weight
            preferences[USER_HEIGHT] = height
            preferences[WORKOUT_COUNT] = workoutCount
            preferences[REMEMBER_ME] = rememberMe
        }
    }

    override suspend fun updateUserDetails(username: String, age: Int, weight: Int, height: Int) {
        dataStore.edit { preferences ->
            preferences[USERNAME] = username
            preferences[USER_AGE] = age
            preferences[USER_WEIGHT] = weight
            preferences[USER_HEIGHT] = height
        }
    }


    override suspend fun clearLoginInfo() {
        dataStore.edit { preferences ->
            preferences.remove(USER_ID)
            preferences.remove(USERNAME)
            preferences.remove(USER_AGE)
            preferences.remove(USER_WEIGHT)
            preferences.remove(USER_HEIGHT)
            preferences.remove(REMEMBER_ME)
            preferences.remove(WORKOUT_COUNT)
        }
    }

    override suspend fun updateWorkoutCount(count: Int) {
        dataStore.edit { preferences ->
            preferences[WORKOUT_COUNT] = count
        }
    }


    override suspend fun saveLanguagePreference(language: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE] = language
        }
    }

    override suspend fun applySavedLanguage() {
        dataStore.data
            .collect { preferences ->
                val savedLanguage = preferences[LANGUAGE] ?: "en"
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(savedLanguage))
            }
    }


}
