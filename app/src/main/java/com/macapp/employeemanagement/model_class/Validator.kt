package com.macapp.employeemanagement.model_class

object Validator {

    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(
            !email.isNullOrEmpty()
        )
    }

    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(!password.isNullOrEmpty() && password.length >= 10)
    }
}

data class ValidationResult(
    val status: Boolean = false
)