package com.komshop.data.retrofit.dto

import com.google.gson.JsonObject

data class NetworkResponse(
    val message: String,
    val error: String?,
    val errors: JsonObject?,
    val data: Any
)
