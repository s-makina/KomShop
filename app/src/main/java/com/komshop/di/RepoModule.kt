package com.komshop.di

import com.komshop.data.repo.AdminHomeRepo
import com.komshop.data.repo.AuctionTypeRepo
import com.komshop.data.repo.AuctionsRepo
import com.komshop.data.repo.BidingRepo
import com.komshop.data.repo.CartRepo
import com.komshop.data.repo.NotificationRepo
import com.komshop.data.repo.SalesRepo
import com.komshop.data.repo.UserRepo
import com.komshop.data.retrofit.RetrofitInterface
import com.komshop.data.room.dao.CartDao
import com.komshop.data.room.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {
    @Singleton
    @Provides
    fun provideUserRepo(retrofitInterface: RetrofitInterface, userDao: UserDao) =
        UserRepo(retrofitInterface = retrofitInterface, userDao)

    @Provides
    fun provideAuctionTypeRepo(retrofitInterface: RetrofitInterface) =
        AuctionTypeRepo(retrofitInterface)

    @Provides
    fun provideAuctionsRepo(retrofitInterface: RetrofitInterface) = AuctionsRepo(retrofitInterface)

    @Provides
    fun provideAdminAuctionRepo(retrofitInterface: RetrofitInterface) =
        AdminHomeRepo(retrofitInterface)

    @Provides
    fun provideSalesRepo(retrofitInterface: RetrofitInterface) = SalesRepo(retrofitInterface)

    @Provides
    fun provideBiddingRepo(retrofitInterface: RetrofitInterface, cartDao: CartDao) = BidingRepo(retrofitInterface, cartDao)

    @Provides
    fun provideNotificationRepo(cartDao: CartDao) = NotificationRepo(cartDao)

    @Provides
    fun provideCartRepo(cartDao: CartDao) = CartRepo(cartDao)

}