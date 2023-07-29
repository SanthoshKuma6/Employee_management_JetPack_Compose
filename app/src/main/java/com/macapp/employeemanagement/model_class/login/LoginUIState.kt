package com.macapp.employeemanagement.model_class.login

data class LoginUIState(
    var registrationNumber: String="",
    var password: String = "",

    var emailError: Boolean = false,
    var passwordError: Boolean = false,
)
