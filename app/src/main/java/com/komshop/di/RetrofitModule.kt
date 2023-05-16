package com.komshop.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.komshop.Config.API_ADDRESS
import com.komshop.data.retrofit.RetrofitInterface
import com.komshop.data.retrofit.OAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideGsonBuilder() : Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit.Builder {
        val client =  OkHttpClient.Builder()
            .addInterceptor(OAuthInterceptor("Bearer"))
            .build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(API_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit.Builder) : RetrofitInterface {
        return retrofit.build()
            .create(RetrofitInterface::class.java)
    }

}