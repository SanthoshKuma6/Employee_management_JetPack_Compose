package com.macapp.employeemanagement.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.macapp.employeemanagement.repository.LoginRepository

class ViewModelFactory(private val mainRepository: LoginRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(mainRepository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}