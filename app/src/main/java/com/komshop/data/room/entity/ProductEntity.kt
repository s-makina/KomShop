package com.komshop.data.room.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.komshop.data.model.MediaItem
import com.komshop.data.model.ProductCategory
import com.komshop.data.model.ProductImage

@Entity("tbl_cart_item", indices = [ Index(value = ["itemId"], unique = true)]  )
class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val itemId: String,
    val name: String,
    val quantity: Int,
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
    val images: List<ProductImage>,
)