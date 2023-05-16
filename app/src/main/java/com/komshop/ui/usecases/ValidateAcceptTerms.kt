package com.komshop.ui.usecases

class ValidateAcceptTerms {
    fun execute(acceptedTerms: Boolean): ValidationResult {
        if (!acceptedTerms) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please accept the terms"
            )
        }
        return ValidationResult(successful = true)
    }
}