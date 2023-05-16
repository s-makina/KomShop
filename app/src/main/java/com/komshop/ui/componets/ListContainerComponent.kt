package com.komshop.ui.componets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.komshop.util.Resource
import com.komshop.util.Status

@Composable
fun ListContainer(
    modifier: Modifier = Modifier,
    res: Resource<Any>? = null,
    errorMessage: String = "",
    notFoundMessage: String = "Items Not Found",
    isEmpty: Boolean = false,
    onRefresh: () -> Unit = {},
    content: LazyListScope.() -> Unit
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (!isEmpty) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
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