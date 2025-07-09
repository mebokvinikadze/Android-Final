package com.example.androidfinal.data.di

import com.example.androidfinal.data.repository.difficulty.DifficultyRepository
import com.example.androidfinal.data.repository.difficulty.DifficultyRepositoryImpl
import com.example.androidfinal.data.repository.gemini.GeminiRepository
import com.example.androidfinal.data.repository.gemini.GeminiRepositoryImpl
import com.example.androidfinal.data.repository.home.HomeRepository
import com.example.androidfinal.data.repository.home.HomeRepositoryImpl
import com.example.androidfinal.data.repository.login.LoginRepository
import com.example.androidfinal.data.repository.login.LoginRepositoryImpl
import com.example.androidfinal.data.repository.profile.ProfileRepository
import com.example.androidfinal.data.repository.profile.ProfileRepositoryImpl
import com.example.androidfinal.data.repository.register.RegisterRepository
import com.example.androidfinal.data.repository.register.RegisterRepositoryImpl
import com.example.androidfinal.data.repository.workout.WorkoutRepository
import com.example.androidfinal.data.repository.workout.WorkoutRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLoginRepository(
        impl: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    @Singleton
    abstract fun bindRegisterRepository(
        impl: RegisterRepositoryImpl
    ): RegisterRepository

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    @Singleton
    abstract fun bindGeminiRepository(
        impl: GeminiRepositoryImpl
    ): GeminiRepository

    @Binds
    @Singleton
    abstract fun bindDifficultyRepository(
        impl: DifficultyRepositoryImpl
    ): DifficultyRepository

    @Binds
    @Singleton
    abstract fun bindWorkoutRepository(
        impl: WorkoutRepositoryImpl
    ): WorkoutRepository

    @Binds
    @Singleton
    abstract fun bindHomeRepository(
        impl: HomeRepositoryImpl
    ): HomeRepository
}