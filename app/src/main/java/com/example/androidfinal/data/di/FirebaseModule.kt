package com.example.androidfinal.data.di

import android.content.Context
import com.example.androidfinal.common.FirebaseHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFireStore(): FirebaseFirestore{
      return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseHelper(@ApplicationContext context: Context): FirebaseHelper {
        return FirebaseHelper(context)
    }
}