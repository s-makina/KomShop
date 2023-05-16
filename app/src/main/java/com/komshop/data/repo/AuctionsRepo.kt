package com.komshop.data.repo

import com.google.gson.Gson
import com.komshop.data.model.AuctionItem
import com.komshop.data.model.AuctionType
import com.komshop.data.retrofit.RetrofitInterface
import com.komshop.data.retrofit.dto.AuctionItemDto
import com.komshop.data.retrofit.dto.AuctionTypeDto
import com.komshop.data.retrofit.toModel
import com.komshop.log
import com.komshop.toArray
import com.komshop.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuctionsRepo(private val retrofitInterface: RetrofitInterface) {
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

    fun getItems(): Flow<Resource<List<AuctionItem>>> = flow {
        emit(Resource.loading(null))
        try {
            val res = retrofitInterface.getAuctionItems()
            emit(Resource.success(res.map { it.toModel() }))
        } catch (e: Exception) {
            log(e.message)
            emit(Resource.error(e.message, null))
        }
    }

    fun getMyBids(): Flow<Resource<List<AuctionItem>>> = flow {
        emit(Resource.loading(null))
        try {
            val res = retrofitInterface.getMyBids()
            log(res)
            val items: List<AuctionItemDto> = Gson().toArray(res.data)
            emit(Resource.success(items.map { it.toModel() }))
        } catch (e: Exception) {
            emit(Resource.error(e.message, null))
        }
    }
}