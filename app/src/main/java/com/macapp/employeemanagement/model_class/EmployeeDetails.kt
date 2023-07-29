package com.macapp.employeemanagement.model_class

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class EmployeeDetails(
    val title:String,
    val domain:String,
    @DrawableRes val iconId:Int?=null,
    val lightColor: Color?=null,
    val darkColor: Color?=null,
    val mediumColor: Color?=null,
)
