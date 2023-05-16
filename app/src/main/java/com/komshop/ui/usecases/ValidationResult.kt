package com.komshop.ui.usecases

data class ValidationResult(
    val successful: Boolean = false,
    val errorMessage: String? = null
)
