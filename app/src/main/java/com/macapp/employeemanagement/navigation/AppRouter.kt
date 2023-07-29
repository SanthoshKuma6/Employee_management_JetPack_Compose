package com.macapp.employeemanagement.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

sealed class Screen{

    object LoginScreen:Screen()
    object HomeScreen:Screen()
    object AddNewEmployee:Screen()
    object ViewProfile:Screen()
    object MyEmployeeScreen:Screen()
}

object AppRouter {
    val currentScreen: MutableState<Screen> = mutableStateOf(Screen.MyEmployeeScreen)


    fun navigateTo(designation:Screen){
        currentScreen.value=designation
    }
}