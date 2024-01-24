package com.example.newsapp.di

import com.example.newsapp.data.remote.repository.NewRepositoryImpl
import com.example.newsapp.data.remote.service.NewService
import com.example.newsapp.domain.repository.NewRepository
import com.example.newsapp.utils.Constants
import com.google.gson.GsonBuilder
import com.itkacher.okprofiler.BuildConfig
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            client.addInterceptor(OkHttpProfilerInterceptor())
        }
        return client.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().create())
        ).client(okHttpClient).build()


    @Provides
    @Singleton
    fun provideNewService(retrofit: Retrofit): NewService {
        return retrofit.create(NewService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewRepository(service: NewService): NewRepository {
        return NewRepositoryImpl(service)
    }

}