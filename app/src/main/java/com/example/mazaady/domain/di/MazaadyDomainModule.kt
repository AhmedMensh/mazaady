package com.example.mazaady.domain.di

import com.example.mazaady.data.repositories.CategoriesRepositoryImpl
import com.example.mazaady.domain.repositories.ICategoriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MazaadyDomainModule {

    @Singleton
    @Binds
    abstract fun bindCategoriesRepositoryImpl(
        categoriesRepositoryImpl: CategoriesRepositoryImpl
    ): ICategoriesRepository
}

