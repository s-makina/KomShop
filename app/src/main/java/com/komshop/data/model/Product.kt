package com.komshop.data.model

import com.komshop.data.retrofit.dto.ProductCategoryDto
import com.komshop.data.retrofit.dto.ProductImageDto

data class Product (
    val id: String,
    val name: String,
    val permalink: String,
    val date_created: String,
    val status: String,
    val featured: Boolean,
    val description: String,
    val shortDescription: String,
    val price: Int,
    val regularPrice: Int,
    val salePrice: Int,
    val on_sale: Boolean,
    val priceHtml: String,
    val stockStatus: String,
    val categories: List<ProductCategory>,
    val images: List<ProductImage>
)