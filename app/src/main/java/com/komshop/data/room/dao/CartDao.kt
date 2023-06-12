package com.komshop.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.komshop.data.room.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg productEntity: ProductEntity)

    @Update
    suspend fun update(productEntity: ProductEntity)

    @Delete
    suspend fun delete(productEntity: ProductEntity)

    @Query("SELECT * FROM tbl_cart_item")
    fun getCartItems(): Flow<List<ProductEntity>>

    @Query("SELECT COUNT(*) FROM tbl_cart_item")
    fun getTotalItems(): Flow<Int>
}