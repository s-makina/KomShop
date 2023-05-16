package com.komshop.ui.pages.shop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.komshop.ui.viewmodel.CheckOutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShippingForm(modifier: Modifier, checkOutViewModel: CheckOutViewModel) {
    val style =
        TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
            focusedLabelColor = MaterialTheme.colorScheme.onPrimary
        )

    val inputModifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = inputModifier,
            value = "",
            onValueChange = {},
            label = { Text(text = "Name") },
            colors = style
        )
        OutlinedTextField(
            modifier = inputModifier,
            value = "",
            onValueChange = {},
            label = { Text(text = "Address") }, colors = style)
        OutlinedTextField(
            modifier = inputModifier,
            value = "",
            onValueChange = {},
            label = { Text(text = "Phone") }, colors = style)
        OutlinedTextField(
            modifier = inputModifier,
            value = "",
            onValueChange = {},
            label = { Text(text = "Alternative Phone") }, colors = style)
        OutlinedTextField(
            modifier = inputModifier,
            value = "",
            onValueChange = {},
            label = { Text(text = "Email") }, colors = style)
    }
}