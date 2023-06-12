package com.komshop.data.repo

import com.komshop.data.model.CartItem
import com.komshop.data.room.dao.CartDao
import com.komshop.data.room.toCartEntity
import com.komshop.data.room.toCartItem
import com.komshop.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartRepo(val cartDao: CartDao) {
    fun getCartItems() : Flow<List<CartItem>> {
        return cartDao.getCartItems().map { list -> list.map { it.toCartItem() } }
    }

    suspend fun removeItem(item: CartItem) {
        cartDao.delete(item.toCartEntity())
    }

    suspend fun updateItem(item: CartItem) {
        log(item.quantity)
        cartDao.update(item.toCartEntity())
    }
}