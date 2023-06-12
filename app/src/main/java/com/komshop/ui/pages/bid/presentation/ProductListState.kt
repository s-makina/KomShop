package com.komshop.ui.pages.bid.presentation

import com.komshop.data.model.Product
import com.komshop.data.model.ProductCategory

data class ProductListState(
    val searchTerm: String = "",
    val categoryId: String = "",
    val selectedAuction: com.komshop.data.Auction? = null,
    val products: List<Product> = emptyList(),
    val productCategories: List<ProductCategory> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 1
)