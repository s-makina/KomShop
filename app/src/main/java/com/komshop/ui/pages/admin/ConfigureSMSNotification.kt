package com.komshop.ui.pages.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.komshop.ui.componets.TopNav

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigureSMSNotification(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopNav(
                navController = navController,
                title = "Set up sms notification"
            )
        }, containerColor = MaterialTheme.colorScheme.primary ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "Message") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4)
        }
    }
}