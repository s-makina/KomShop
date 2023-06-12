package com.komshop.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.komshop.Config.app_version
import com.komshop.data.room.dao.CartDao
import com.komshop.data.room.dao.UserDao
import com.komshop.data.room.entity.ProductEntity
import com.komshop.data.room.entity.UserEntity

@Database(
    entities = [UserEntity::class, ProductEntity::class],
    version = app_version
)
@TypeConverters(Convertors::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "auction_db_$app_version"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}