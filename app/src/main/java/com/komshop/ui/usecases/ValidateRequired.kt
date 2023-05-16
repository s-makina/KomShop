package com.komshop.ui.usecases

class ValidateRequired {
    fun execute(text: String): ValidationResult {
        if (text.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Required"
            )
        }
        return ValidationResult(successful = true)
    }
}