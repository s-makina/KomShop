package com.komshop.data.retrofit.dto

import androidx.annotation.Keep

@Keep
data class ProductDto(
    val id: String,
    val name: String,
    val permalink: String,
    val date_created: String,
    val status: String,
    val featured: Boolean,
    val description: String,
    val short_description: String,
    val price: String,
    val regular_price: String,
    val sale_price: String,
    val on_sale: Boolean,
    val price_html: String,
    val stock_status: String,
    val categories: List<ProductCategoryDto>,
    val images: List<ProductImageDto>
)