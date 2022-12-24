package com.example.mazaady.data.network.di

import com.example.mazaady.data.network.MazaadyApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MazaadyServicesModule {
    @Singleton
    @Provides
    fun provideMazaadyApiService(retrofit: Retrofit) =
        retrofit.create(MazaadyApiService::class.java)
}