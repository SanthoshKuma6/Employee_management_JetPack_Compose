package com.macapp.employeemanagement.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.macapp.employeemanagement.screens.HomeScreen
import com.macapp.employeemanagement.screens.ui.theme.EmployeeManagementTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmployeeManagementTheme {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                )
                HomeScreen()
            }
        }

    }
}

