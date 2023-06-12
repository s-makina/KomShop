package com.komshop.data.repo

import com.komshop.data.retrofit.RetrofitInterface
import com.komshop.data.room.dao.CartDao
import com.komshop.data.room.entity.ProductEntity
import com.komshop.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BidingRepo(val retrofitInterface: RetrofitInterface, val cartDao: CartDao) {

    fun placeAbid(itemId: String, bidAmount: String) : Flow<Resource<String>> = flow {
        emit(Resource.loading(null))
        try {
            val res = retrofitInterface.placeAbid(
                itemId = itemId,
                bidAmount = bidAmount
            )
            if (res.error != null) throw Exception(res.error)
            emit(Resource.success("Your bid is placed."))
        } catch (e: Exception) {
            emit(Resource.error(e.message, null))
        }
    }

    suspend fun addToCart(product: ProductEntity) {
        cartDao.insert(product)
    }
}