package com.komshop.data.repo

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.komshop.data.model.AuctionItem
import com.komshop.data.model.AuctionType
import com.komshop.data.model.Product
import com.komshop.data.model.ProductCategory
import com.komshop.data.retrofit.RetrofitInterface
import com.komshop.data.retrofit.dto.AuctionItemDto
import com.komshop.data.retrofit.dto.AuctionTypeDto
import com.komshop.data.retrofit.toModel
import com.komshop.data.retrofit.toProduct
import com.komshop.data.retrofit.toProductCategory
import com.komshop.log
import com.komshop.toArray
import com.komshop.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class AuctionsRepo(private val retrofitInterface: RetrofitInterface ) {
    fun getAuctions(type: String): Flow<Resource<List<AuctionType>>> = flow {
        emit(Resource.loading(null))
        try {
            log(type)
            // TODO live to type
            val res = retrofitInterface.getAuctions("live")
            val auctions: List<AuctionTypeDto> = Gson().toArray(res.data)
            emit(Resource.success(auctions.map { it.toModel() }))
        } catch (e: Exception) {
            log(e.message)
            emit(Resource.error(e.message, null))
        }
    }

//    fun getProducts(): Flow<Resource<List<Product>>> = flow {
//        emit(Resource.loading(null))
//        try {
//            val res = retrofitInterface.getProducts()
//            log(res)
//            emit(Resource.success(res.map { it.toProduct() }))
//        } catch (e: Exception) {
//            log(e.message)
//            emit(Resource.error(e.message, null))
//        }
//    }

    /**
     * Gets a list of songs paginated
     *
     * @param page current loaded page
     * @param pageSize how many to load
     * @return list of wrapped in result data type
     */
    suspend fun getItemsPaginated(page: Int, pageSize: Int, searchTerm: String, categoryId: String): Result<List<Product>> {
        return try {
            val res = retrofitInterface.getPaginatedProductList(page = page, pageSize = pageSize, searchTerm = searchTerm, category = categoryId)
            Result.success(res.map { it.toProduct() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getProductCategories(): Flow<Resource<List<ProductCategory>>> = flow {
        emit(Resource.loading(null))
        try {
            val res = retrofitInterface.getProductCategory()
            emit(Resource.success(res.map { it.toProductCategory() }))
        } catch (e: Exception) {
            emit(Resource.error(e.message, null))
        }
    }

    fun getMyBids(): Flow<Resource<List<AuctionItem>>> = flow {
        emit(Resource.loading(null))
        try {
            val res = retrofitInterface.getMyBids()
            log(res)
            val items: List<AuctionItemDto> = Gson().toArray(res.data)
            log(items)
            emit(Resource.success(items.map { it.toModel() }))
        } catch (e: Exception) {
            emit(Resource.error(e.message, null))
        }
    }
}