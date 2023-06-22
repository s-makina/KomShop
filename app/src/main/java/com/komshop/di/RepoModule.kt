package com.komshop.di

import android.content.Context
import com.komshop.data.repo.AuctionsRepo
import com.komshop.data.repo.BidingRepo
import com.komshop.data.repo.CartRepo
import com.komshop.data.repo.NotificationRepo
import com.komshop.data.retrofit.RetrofitInterface
import com.komshop.data.room.dao.CartDao
import com.komshop.data.room.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {
    @Provides
    fun provideAuctionsRepo(retrofitInterface: RetrofitInterface) = AuctionsRepo(retrofitInterface)

    @Provides
    fun provideBiddingRepo(retrofitInterface: RetrofitInterface, cartDao: CartDao) = BidingRepo(retrofitInterface, cartDao)

    @Provides
    fun provideNotificationRepo(cartDao: CartDao) = NotificationRepo(cartDao)

    @Provides
    fun provideCartRepo(cartDao: CartDao, retrofitInterface: RetrofitInterface) = CartRepo(retrofitInterface, cartDao)

}