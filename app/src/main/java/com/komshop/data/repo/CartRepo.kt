package com.komshop.data.repo

import com.komshop.data.model.CartItem
import com.komshop.data.retrofit.RetrofitInterface
import com.komshop.data.retrofit.dto.BillingDto
import com.komshop.data.retrofit.dto.OrderDto
import com.komshop.data.room.dao.CartDao
import com.komshop.data.room.toCartEntity
import com.komshop.data.room.toCartItem
import com.komshop.data.room.toLineItem
import com.komshop.log
import com.komshop.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CartRepo(val retrofitInterface: RetrofitInterface, val cartDao: CartDao) {
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

    suspend fun submitOrder(
        firstName: String,
        lastName: String,
        city: String,
        phone: String,
        items: List<CartItem>
    ): Flow<Resource<String>> = flow {
        emit(Resource.loading(null))
        try {
            val billing = BillingDto(
                first_name = firstName,
                last_name = lastName
            )
            val order = OrderDto(
                billing = billing,
                line_items = items.map { it.toLineItem() }
            )
            val res = retrofitInterface.submitOrder(order)

            log(res.string())
            emit(Resource.success("Submitted successfully"))
        } catch (e: Exception) {
            log(e.message)
            emit(Resource.error(null, e.message))
        }
    }

    suspend fun clearItems() {
        cartDao.clear()
    }
}