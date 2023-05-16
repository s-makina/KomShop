package com.komshop.ui.usecases

class ValidateDepositSlip {
    fun execute(deposit: String): ValidationResult {
        if (deposit.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Enter DepositSlip Number"
            )
        } else if (deposit.length < 4) {
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid Deposit Slip Ref. Number"
            )
        }
        return ValidationResult(successful = true)
    }
}