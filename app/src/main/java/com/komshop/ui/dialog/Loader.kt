package com.komshop.ui.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ProcessLoader(process: MutableState<Boolean>) {
    if (process.value)
        Dialog(onDismissRequest = { }) {
            Card() {
                Column(
                    Modifier
                        .width(200.dp)
                        .padding(10.dp), verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Loading...")
                    Spacer(modifier = Modifier.height(12.dp))
//                   TextButton(onClick = {process.value = State.CANCELED}) {
//                       Text(text = "Cancel")
//                   }
                }

            }

        }
}
