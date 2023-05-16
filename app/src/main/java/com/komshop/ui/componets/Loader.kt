package com.komshop.ui.componets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.komshop.Config.process

enum class State{
    ERROR,
    LOADING,
    CANCELED,
    IDLE
}

@Preview(showBackground = true)
@Composable
fun Loader() {
    if (process.value == State.LOADING)
        Dialog(onDismissRequest = {  }){
            Card() {
                Column(
                    Modifier
                        .size(200.dp)
                        .padding(10.dp), verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(12.dp))
//                    CircularProgressIndicator()
                    ProgressLoader()
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

