package com.komshop.ui.pages.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeStep(
    icon: @Composable () -> Unit = {},
    title: String,
    body: String
) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp), contentAlignment = Alignment.BottomCenter
        ) {
            icon()
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 30.sp),
            textAlign = TextAlign.Center
        )

        Text(
            text = body,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }

}