package com.komshop.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.komshop.Config
import com.komshop.Config.API_ADDRESS
import com.komshop.Config.CONSUMER_KEY
import com.komshop.Config.SECRET_KEY
import com.komshop.data.retrofit.BasicAuthInterceptor
import com.komshop.data.retrofit.OAuth1Interceptor
import com.komshop.data.retrofit.RetrofitInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
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

    private val random: SecureRandom = SecureRandom()

    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit.Builder {
        val oauth1Woocommerce: OAuth1Interceptor = OAuth1Interceptor.Builder()
            .consumerKey(Config.CONSUMER_KEY)
            .consumerSecret(Config.SECRET_KEY)
            .build()

        val client =  OkHttpClient.Builder()
//            .addInterceptor(OAuthInterceptor("Bearer"))
            .addInterceptor(oauth1Woocommerce)
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