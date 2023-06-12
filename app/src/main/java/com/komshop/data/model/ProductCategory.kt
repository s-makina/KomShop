package com.komshop.data.model

import androidx.annotation.Keep

@Keep
class ProductCategory(
    val id: String,
    val name: String,
    val slug: String
)