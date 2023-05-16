package com.komshop.data.room.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.komshop.data.model.MediaItem

@Entity("tbl_cart_item", indices = [ Index(value = ["itemId"], unique = true)]  )
class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val itemId: String,
    val quantity: Int,
    val name: String,
    val description: String?,
    val currentBid: Double,
    val image: String,
    val media: List<MediaItem>
)