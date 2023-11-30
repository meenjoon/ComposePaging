package com.mbj.composepaging.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mbj.composepaging.data.remote.network.adapter.ApiCallAdapterFactory
import com.mbj.composepaging.data.remote.network.api.rick_morty.RickMortyApi
import com.mbj.composepaging.data.remote.network.api.rick_morty.RickMortyApiClient
import com.mbj.composepaging.data.remote.network.api.rick_morty.repository.RickMortyPagingDataSource
import com.mbj.composepaging.data.remote.network.api.rick_morty.repository.RickMortyRepository
import okhttp3.MediaType.Companion.toMediaType
import com.mbj.composepaging.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        BASE_URL: String,
        json: Json
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(ApiCallAdapterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideRickMortyApiService(retrofit: Retrofit): RickMortyApiClient {
        return retrofit.create(RickMortyApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideRickMortyApi(rickMortyPagingDataSource: RickMortyPagingDataSource): RickMortyApi {
        return RickMortyRepository(rickMortyPagingDataSource)
    }
}
