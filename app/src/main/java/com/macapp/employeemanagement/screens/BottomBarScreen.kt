package com.macapp.employeemanagement.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route:String,
    val title:String,
    val icon : ImageVector,
){
    object MyEmployee : BottomBarScreen(
        route="MyEmployee",
        title = "MyEmployee",
        icon = Icons.Default.Home,
    )
    object Profile :BottomBarScreen(
        route = "Profile",
        title = "Profile",
        icon = Icons.Default.Person,
    )
}
