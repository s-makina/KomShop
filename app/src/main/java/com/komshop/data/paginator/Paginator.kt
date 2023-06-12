package com.komshop.data.paginator

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}