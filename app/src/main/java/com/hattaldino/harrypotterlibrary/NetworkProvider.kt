package com.hattaldino.harrypotterlibrary

import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import com.hattaldino.harrypotterlibrary.BuildConfig

object NetworkProvider {

    fun providesHttpAdapter(): Retrofit {
        return Retrofit.Builder().apply {
            client(providesHttpClient())
            baseUrl(BuildConfig.BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }

    private fun providesHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            retryOnConnectionFailure(true)
            addInterceptor(providesHttpLoggingIntereptor())
        }.build()
    }

    private fun providesHttpLoggingIntereptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = when(BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                false -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }
}