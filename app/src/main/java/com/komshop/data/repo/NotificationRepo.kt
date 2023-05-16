package com.komshop.data.repo

import com.komshop.data.room.dao.CartDao
import kotlinx.coroutines.flow.Flow

class NotificationRepo(
    private val cartDao: CartDao
) {
    fun getTotalCartItems(): Flow<Int> {
        return cartDao.getTotalItems()
    }
}