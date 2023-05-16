package com.komshop.ui.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ScannerDialog(
    showDialog: MutableState<Boolean> = mutableStateOf(false),
    onScanComplete: (String) -> Unit
) {
    if (showDialog.value) {
        Dialog(onDismissRequest = { showDialog.value = false }) {
            Card(
                modifier = Modifier
//                .fillMaxWidth()
                    .size(300.dp)
                    .padding(8.dp)
            ) {
                CameraPreview(onScanComplete = {
                    onScanComplete(it)
//        code.value = it
//        scanning.value = false
//        gadgetViewModel.getGadgets(code = it)
                })
            }
        }
    }
}