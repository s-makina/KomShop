package com.komshop.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.komshop.ui.viewmodel.BidingViewModel
import com.komshop.util.Resource
import com.komshop.util.Status

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlobalUiHandler(
    modifier: Modifier = Modifier,
    biddingViewModel: BidingViewModel,
    content: @Composable () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val loadingState =
        biddingViewModel.loadingEvent.collectAsStateWithLifecycle(initialValue = null)

    Scaffold(modifier = modifier,
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
            ) { snackBarData ->
                CustomSnackBar(
                    loadingState.value,
                    snackBarData.visuals.message
                ) { snackBarData.dismiss() }
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            content()
        }
    }
}
@Composable
fun CustomSnackBar(res: Resource<Any>?, message: String, onHide: () -> Unit) {
    var containerColor = MaterialTheme.colorScheme.onPrimary
    var contentColor = MaterialTheme.colorScheme.primary

    when (res?.status) {
        Status.LOADING -> {}
        Status.SUCCESS -> {
            containerColor = MaterialTheme.colorScheme.onPrimary
        }
        Status.ERROR -> {
            containerColor = MaterialTheme.colorScheme.errorContainer
        }
        else -> {}
    }

    Snackbar(
        containerColor = containerColor,
        contentColor = contentColor,
        modifier = Modifier.padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            if (res?.status == Status.LOADING) {
                CircularProgressIndicator(modifier = Modifier.size(28.dp))
            } else {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
            }

            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )

            TextButton(onClick = { onHide() }) {
                Text(text = "Hide")
            }
        }
    }
}



