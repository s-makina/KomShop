package com.komshop.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komshop.ui.theme.lightGreyColor

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
    fontSize: TextUnit = 14.sp
) {
    var text by remember { mutableStateOf("") }
    BasicTextField(modifier = modifier
        .background(
            lightGreyColor.copy(alpha = 0.1f),
            MaterialTheme.shapes.small,
        )
        .fillMaxWidth(),
        value = text,
        onValueChange = {
            text = it
        },
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
//        color = MaterialTheme.colors.onSurface.copy(alpha = 0.4f),
//        fontSize = fontSize,
//        textStyle = LocalTextStyle.current.copy(
//            color = onSecondaryContainer,
//            fontSize = fontSize
//        ),
        decorationBox = { innerTextField ->
            Row(
                modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f)) {
                    if (text.isEmpty()) Text(
                        placeholderText,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        fontSize = fontSize,
                    )
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        }
    )
}

@Composable
fun UnderLinedEditText(
    modifier: Modifier = Modifier,
    value: String,
    placeholderText: String,
    onValueChange: (String) -> Unit,
    defaultInputColor: Color = MaterialTheme.colorScheme.onPrimary,
    errorColor: Color = MaterialTheme.colorScheme.onErrorContainer,
    fontSize: TextUnit = 18.sp,
    isError: Boolean = false,
    textAlign: TextAlign = TextAlign.Center,
    contentAlignment: Alignment = Alignment.Center,
    readOnly: Boolean = false
) {
    val dividerState = remember {
        mutableStateOf(true)
    }
    val inputColor = if (isError) errorColor else defaultInputColor
    BasicTextField(
        value = value,
        readOnly = readOnly,
        onValueChange = onValueChange,
        cursorBrush = SolidColor(inputColor),
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                dividerState.value = !it.isFocused
            },
        decorationBox = { innerTextField ->
            val dividerColor =
                if (dividerState.value) inputColor.copy(alpha = ContentAlpha.disabled) else
                    inputColor.copy(alpha = ContentAlpha.high)
            Column(Modifier.fillMaxWidth()) {
                Box(Modifier.fillMaxWidth(), contentAlignment = contentAlignment) {
                    if (value.isEmpty()) Text(
                        placeholderText,
                        color = inputColor.copy(alpha = 0.3f),
                        fontSize = fontSize,
                    )
                    innerTextField()
                }

                Divider(
                    thickness = 1.3.dp, color = dividerColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = inputColor,
            fontSize = fontSize,
            textAlign = textAlign
        ),
        )
}
