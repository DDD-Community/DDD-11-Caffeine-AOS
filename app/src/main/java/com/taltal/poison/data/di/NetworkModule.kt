package com.taltal.poison.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.taltal.poison.data.api.PoisonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            ).build()

    @Provides
    @Singleton
    fun provideRetrofit(
        serializer: Json,
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("http://138.2.112.144:8080")
        .addConverterFactory(serializer.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides
    @Singleton
    fun providePoisonApi(retrofit: Retrofit): PoisonApi =
        retrofit.create(PoisonApi::class.java)
}

