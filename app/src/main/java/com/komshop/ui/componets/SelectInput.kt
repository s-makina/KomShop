package com.komshop.ui.componets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SelectInput(
//    modifier: Modifier = Modifier,
//    selectedOptionText: String = "",
//    options: List<String>,
//    placeHolder: String = "",
//    isError: Boolean,
//    onSelected: (String) -> Unit
//) {
//
//    var expanded by remember { mutableStateOf(false) }
//// We want to react on tap/press on TextField to show menu
//
//    ExposedDropdownMenuBox(
//        modifier = modifier,
//        expanded = expanded,
//        onExpandedChange = { expanded = !expanded },
//    ) {
////        TextField(
////            // The `menuAnchor` modifier must be passed to the text field for correctness.
////            modifier = Modifier.menuAnchor(),
////            readOnly = true,
////            value = selectedOptionText,
////            onValueChange = {},
////            label = { Text("Label") },
////            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
////            colors = ExposedDropdownMenuDefaults.textFieldColors(),
////        )
//
//        UnderLinedEditText(
//            value = selectedOptionText,
//            onValueChange = {},
//            modifier = Modifier
//                .fillMaxWidth()
//                .menuAnchor(),
//            placeholderText = placeHolder,
//            readOnly = true,
//            isError = isError
//        )
//
//        ExposedDropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//        ) {
//            options.forEach { selectionOption ->
//                DropdownMenuItem(
//                    text = { Text(selectionOption) },
//                    onClick = {
////                        selectedOptionText.value = selectionOption
//                        onSelected(selectionOption)
//                        expanded = false
//                    },
//                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
//                )
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectInput(
    modifier: Modifier = Modifier,
    selectedOptionText: String = "",
    options: List<String>,
    placeHolder: String = "",
    isError: Boolean,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
// We want to react on tap/press on TextField to show menu
    ExposedDropdownMenuBox(
        modifier = modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OutlinedTextField(
            // The `menuAnchor` modifier must be passed to the text field for correctness.
            modifier = Modifier.fillMaxWidth().menuAnchor(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text(placeHolder) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            isError = isError
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
//                        selectedOptionText = selectionOption
                        onSelected(selectionOption)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}