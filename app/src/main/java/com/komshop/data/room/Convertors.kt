 package com.komshop.data.room

import androidx.room.TypeConverter
import com.komshop.data.model.MediaItem
import com.komshop.data.model.ProductCategory
import com.komshop.data.model.ProductImage
import java.net.URLDecoder
import java.net.URLEncoder

 class Convertors {
    @TypeConverter
    fun arrayToString(list: List<String>): String {
        return list.joinToString(separator = "**") { it }
    }

    @TypeConverter
    fun arrayToString(text: String): List<String> {
        return text.split("**")
    }

     @TypeConverter
     fun categoryListToString(list: List<ProductCategory>): String {
         return list.joinToString("**") { it.id+"=>"+it.name+"=>"+it.slug }
     }

     @TypeConverter
     fun stringToCategoryArray(text: String): List<ProductCategory> {
         return text.split("**").map {
             val arr = it.split("=>")
             ProductCategory(
                 id = arr.first(),
                 name = arr[1],
                 slug = arr.last()
             )
         }
     }

     @TypeConverter
     fun imageListToString(list: List<ProductImage>): String {
         return list.joinToString("**") { it.src }
     }

     @TypeConverter
     fun stringToProductImageArray(text: String): List<ProductImage> {
         return text.split("**").map {
             ProductImage(
                src = it
             )
         }
     }

     @TypeConverter
    fun mediaItemToString(list: List<MediaItem>): String {
        return list.joinToString(separator = "**") { URLEncoder.encode(it.originalUrl, "UTF-8") }
    }

     @TypeConverter
     fun arrayToMediaItem(text: String): List<MediaItem> {
         return text.split("**").map { MediaItem(URLDecoder.decode(it, "UTF-8")) }
     }

}