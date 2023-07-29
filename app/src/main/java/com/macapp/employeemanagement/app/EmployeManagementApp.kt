package com.macapp.employeemanagement.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.macapp.employeemanagement.navigation.AppRouter
import com.macapp.employeemanagement.navigation.Screen
import com.macapp.employeemanagement.screens.AddNewEmployee
import com.macapp.employeemanagement.screens.HomeScreen
import com.macapp.employeemanagement.screens.LoginScreen
import com.macapp.employeemanagement.screens.ViewProfile

@Composable
fun EmployeeManagement() {
    Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
        LoginScreen()


//        Crossfade(targetState = AppRouter.currentScreen) {currentScreen->
//            when(currentScreen.value){
//                is Screen.HomeScreen->{
//                    HomeScreen()
//                }
//                is Screen.AddNewEmployee->{
//                    AddNewEmployee()
//                }
//                is Screen.ViewProfile->{
//                    ViewProfile()
//                }
//
//                else -> {}
//            }
//
//        }
    }

}