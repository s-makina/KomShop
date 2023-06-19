package com.komshop.ui.pages.shop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.komshop.ui.events.CheckOutEvent
import com.komshop.ui.viewmodel.CheckOutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShippingForm(modifier: Modifier, checkOutViewModel: CheckOutViewModel) {
    val state = checkOutViewModel.state
    val style =
        OutlinedTextFieldDefaults.colors(
//            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
//            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
//            focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
//            unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
        )

    val inputModifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = inputModifier,
            value = state.firstName,
            onValueChange = {
                checkOutViewModel.event(CheckOutEvent.OnFirstNameChange(it))
            },
            label = { Text(text = "Name") },
            colors = style,
            isError = state.firstNameError != null
        )
        OutlinedTextField(
            modifier = inputModifier,
            value = state.lastName,
            onValueChange = {
                checkOutViewModel.event(CheckOutEvent.OnLastNameChange(it))
            },
            label = { Text(text = "City") }, colors = style,
            isError = state.lastNameError != null
        )

        OutlinedTextField(
            modifier = inputModifier,
            value = state.phone,
            onValueChange = {
                checkOutViewModel.event(CheckOutEvent.OnPhoneChange(it))
            },
            label = { Text(text = "Phone") }, colors = style,
            isError = state.phoneError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

//        OutlinedTextField(
//            modifier = inputModifier,
//            value = "",
//            onValueChange = {},
//            label = { Text(text = "Alternative Phone") }, colors = style)

        OutlinedTextField(
            modifier = inputModifier,
            value = "",
            onValueChange = {},
            label = { Text(text = "Email") }, colors = style)
    }
}