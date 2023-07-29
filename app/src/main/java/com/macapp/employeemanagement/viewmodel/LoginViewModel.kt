package com.macapp.employeemanagement.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.macapp.employeemanagement.model_class.EmployeeDetails
import com.macapp.employeemanagement.model_class.Validator
import com.macapp.employeemanagement.model_class.login.LoginUIEvent
import com.macapp.employeemanagement.model_class.login.LoginUIState
import com.macapp.employeemanagement.network.ApiService
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var loginUIState = mutableStateOf(LoginUIState())
    var loginInProcess = mutableStateOf(false)
    var allValidationPassed = mutableStateOf(false)
    var TAG=LoginViewModel::class.simpleName


      fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(email = event.email)
                printState()
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(password = event.password)
                printState()
            }

            is LoginUIEvent.LoginButtonClicked -> {
                login()
            }
        }
        validationWithRules()
    }


    private fun validationWithRules() {
        val emailResult = Validator.validateEmail(email = loginUIState.value.email)
        val passwordResult = Validator.validatePassword(password = loginUIState.value.password)

        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status, passwordError = passwordResult.status
        )

        allValidationPassed.value = emailResult.status && passwordResult.status
    }

    private fun printState() {
        Log.d(TAG, "printState:")
        Log.d(TAG, loginUIState.value.toString())

    }

    var movieListResponse:List<LoginUIEvent> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")
     fun login() {
        loginInProcess.value = true
        val emailAddress = loginUIState.value.email
        val password = loginUIState.value.password

        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {

                val movieList = apiService.login(emailAddress,password)
                movieListResponse = movieList

            }

            catch (e: Exception) {
                errorMessage = e.message.toString()
            }

        }
        Log.d("emailResult", "validationWithRules: $emailAddress")
        Log.d("passwordResult", "validationWithRules: $password")

    }

}