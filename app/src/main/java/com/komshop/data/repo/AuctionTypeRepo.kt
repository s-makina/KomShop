package com.komshop.data.repo

import com.google.gson.Gson
import com.komshop.data.model.AuctionType
import com.komshop.data.retrofit.RetrofitInterface
import com.komshop.data.retrofit.dto.AuctionTypeDto
import com.komshop.data.retrofit.toModel
import com.komshop.toArray
import com.komshop.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuctionTypeRepo(private val retrofitInterface: RetrofitInterface) {
    suspend fun getAuctionTypes(): Flow<Resource<List<AuctionType>>> = flow {
        emit(Resource.loading(null))
        try {
            val res = retrofitInterface.getAuctionTypes()
            val types : List<AuctionTypeDto> = Gson().toArray(res.data)
            emit(Resource.success(types.map { it.toModel() }))
        } catch (e: Exception) {
            emit(Resource.error(e.message, null))
        }
    }

    fun submitDepositSlip(auctionTypeId: String, currentDepositSlipNumber: String): Flow<Resource<String>> = flow {
        emit(Resource.loading(null))
        try {
            val res = retrofitInterface.submitDepositRef(referenceNumber =currentDepositSlipNumber,  auctionTypeId = auctionTypeId)
            if (res.error != null) throw Exception(res.error)
            emit(Resource.success(""))
        } catch (e: Exception) {
            emit(Resource.error(e.message, null))
        }
    }

}