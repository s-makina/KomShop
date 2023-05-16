package com.komshop.ui.componets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.komshop.util.Resource
import com.komshop.util.Status

@Composable
fun GridContainerComponent(
    res: Resource<Any>? = null,
    errorMessage: String = "",
    notFoundMessage: String = "Items Not Found",
    isEmpty: Boolean = false,
    onRefresh: () -> Unit = {},
    content: LazyGridScope.() -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (!isEmpty) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize().padding(end = 8.dp, top = 8.dp)
            ) {
                content()
            }
        } else {
            when (res?.status) {
                Status.LOADING -> {
                    ProgressLoader()
                }
                Status.ERROR -> {
                    ConnectionError(retry = "Retry"){
                        onRefresh()
                    }
                }
                else -> {
                    NotFoundComponent( description = notFoundMessage, retry = "Retry") {
                        onRefresh()
                    }
                }
            }
        }
    }
}