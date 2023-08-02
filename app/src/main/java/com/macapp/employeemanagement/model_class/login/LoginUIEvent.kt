package com.macapp.employeemanagement.model_class.login

sealed class LoginUIEvent {
    data class RegistrationNumberChanged(val registrationNumber: String) : LoginUIEvent()
    data class PasswordChanged(val password: String) : LoginUIEvent()
    object LoginButtonClicked : LoginUIEvent()
}
