package com.komshop.ui.usecases

class ValidateAddress {
    fun execute(address: String): ValidationResult {
        if (address.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The name can't be blank"
            )
        }
        return ValidationResult(successful = true)
    }
}