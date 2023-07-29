package com.macapp.employeemanagement.activity.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.macapp.employeemanagement.activity.ui.theme.ui.theme.EmployeeManagementTheme
import com.macapp.employeemanagement.screens.ViewProfile

class ProfileViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmployeeManagementTheme {
              ViewProfile()
            }
        }
    }
}

