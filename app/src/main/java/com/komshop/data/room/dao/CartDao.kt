package com.komshop.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.komshop.data.room.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg cartItemEntity: CartItemEntity)

    @Update
    suspend fun update(cartItemEntity: CartItemEntity)

    @Delete
    suspend fun delete(cartItemEntity: CartItemEntity)

    @Query("SELECT * FROM tbl_cart_item")
    fun getCartItems(): Flow<List<CartItemEntity>>

    @Query("SELECT COUNT(*) FROM tbl_cart_item")
    fun getTotalItems(): Flow<Int>
}