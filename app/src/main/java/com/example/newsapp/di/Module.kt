package com.example.newsapp.di

import com.example.newsapp.data.api.NewService
import com.example.newsapp.data.repository.NewRepositoryImpl
import com.example.newsapp.domain.repository.NewRepository
import com.example.newsapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

class Module {
    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideNewService(retrofit: Retrofit): NewService {
        return retrofit.create(NewService::class.java)
    }

    @Singleton
    @Provides
    fun provideNewRepository(service: NewService): NewRepository {
        return NewRepositoryImpl(service)
    }
}