 package com.komshop.data.room

import androidx.room.TypeConverter
import com.komshop.data.model.MediaItem
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
    fun mediaItemToString(list: List<MediaItem>): String {
        return list.joinToString(separator = "**") { URLEncoder.encode(it.originalUrl, "UTF-8") }
    }

     @TypeConverter
     fun arrayToMediaItem(text: String): List<MediaItem> {
         return text.split("**").map { MediaItem(URLDecoder.decode(it, "UTF-8")) }
     }

}