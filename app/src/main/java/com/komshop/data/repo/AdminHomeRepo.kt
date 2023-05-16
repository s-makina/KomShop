package com.komshop.data.repo

import com.komshop.data.retrofit.RetrofitInterface
import com.komshop.log
import com.komshop.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AdminHomeRepo(private val retrofitInterface: RetrofitInterface) {

    suspend fun getUser(code: String): Flow<Resource<String>> = flow {
        emit(Resource.loading(null))
        try {
            val res = retrofitInterface.getUser(code)
            log(res)
            if (res.error != null) {
                throw Exception(res.error)
            }
            emit(Resource.success(""))
        } catch (e: Exception) {
            emit(Resource.error(e.message, null))
        }
    }
}