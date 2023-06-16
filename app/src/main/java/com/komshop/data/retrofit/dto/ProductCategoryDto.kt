package com.komshop.data.retrofit.dto

import androidx.annotation.Keep

@Keep
data class ProductCategoryDto(
    val id: String,
    val name: String,
    val slug: String
)