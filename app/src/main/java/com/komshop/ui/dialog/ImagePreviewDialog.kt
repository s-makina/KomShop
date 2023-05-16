package com.komshop.ui.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.request.ImageRequest
import com.komshop.ui.componets.ImagePreview
import com.komshop.ui.componets.PageBackground

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ImagePreviewDialog(url: String, showDialog: MutableState<Boolean>) {
    if (showDialog.value) {
        Dialog(
            onDismissRequest = { showDialog.value = false },
            properties = DialogProperties(usePlatformDefaultWidth = false),
        ) {
            PageBackground(modifier = Modifier.fillMaxSize(9f)) {
                Box(modifier = Modifier.fillMaxSize()) {

                    ImagePreview(url)

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(8f)
                            .align(Alignment.TopCenter),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val color = MaterialTheme.colorScheme.onPrimary
                        Text(text = "Image Preview", style = MaterialTheme.typography.titleLarge)
                        Text(
                            text = "You can zoom, rotate and drag the image\nto get a better view.",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                    IconButton(
                        onClick = { showDialog.value = false },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                }
            }
        }
    }
}

@Composable
fun getImageRequest(url: String): ImageRequest {
    return ImageRequest.Builder(LocalContext.current)
        .data(url)
        .crossfade(true)
        .build()
}