package com.komshop.ui.usecases

class ValidatePhone {
    fun execute(phone: String): ValidationResult {
        if (phone.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email cant be blank"
            )
        }

//        if (!Patterns.PHONE.matcher(phone).matches()) {
//            return ValidationResult(
//                successful = false,
//                errorMessage = "That's not a valid phone number"
//            )
//        }

        return ValidationResult(successful = true)
    }
}