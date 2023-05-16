package com.komshop.ui.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@Composable
fun Alert(
    showDialog: MutableState<Boolean> = mutableStateOf(true),
    title: String? = null,
    content: String,
    alertType: AlertType,
    onClose: () -> Unit = {}
) {
    val icon = when (alertType) {
        AlertType.ERROR -> Icons.Default.Error
        AlertType.INFO -> Icons.Default.Info
        AlertType.SUCCESS -> Icons.Default.Check
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                onClose()
                showDialog.value = false
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog.value = false
                    onClose()
                }) {
                    Text(text = "Close")
                }
            },
            properties = DialogProperties(),

            title = {
                if (title != null) Text(text = title)
            },
            text = {
                Text(
                    text = content,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            icon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    modifier = Modifier.size(40.dp)
                )
            },
            confirmButton = {}
        )
    }
}

enum class AlertType {
    ERROR,
    SUCCESS,
    INFO
}

@Composable
fun ConfirmDaialog(
    showDialog: MutableState<Boolean> = mutableStateOf(true),
    title: String,
    message: String,
    confirmMsg: String = "Proceed",
    onProceed: () -> Unit = {},
    onClose: () -> Unit = {},
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
//                onClose()
                showDialog.value = false
            },
            properties = DialogProperties(),

            title = {
                Text(text = title)
            },
            text = {
                Text(
                    text = message,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.QuestionMark,
                    contentDescription = "",
                    modifier = Modifier.size(40.dp)
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    onProceed()
                }) {
                    Text(text = confirmMsg)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog.value = false
                    onClose()
                }) {
                    Text(text = "Cancel")
                }
            },
        )
    }
}

@Composable
fun DepositSlipAdded(
    showDialog: MutableState<Boolean> = mutableStateOf(true),
    addAnother: () -> Unit = {},
    onClose: () -> Unit = {}
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
//                onClose()
//                showDialog.value = false
            },
            properties = DialogProperties(),

            title = {
                Text(text = "Success")
            },
            text = {
                Text(
                    text = "Your deposit slip have been verified, would you like to enter another?",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.ShapeLine,
                    contentDescription = "",
                    modifier = Modifier.size(40.dp)
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    addAnother()
                }) {
                    Text(text = "Enter Another")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog.value = false
                    onClose()
                }) {
                    Text(text = "Close")
                }
            },
        )
    }
}

