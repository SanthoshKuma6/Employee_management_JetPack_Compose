package com.macapp.employeemanagement.model_class

object Validator {

    fun validateEmail(registrationNumber: String): ValidationResult {
        return ValidationResult(
            (!registrationNumber.isNullOrEmpty())
        )
    }

    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(
            (!password.isNullOrEmpty() ))
    }
}

data class ValidationResult(
    val status: Boolean = false
)