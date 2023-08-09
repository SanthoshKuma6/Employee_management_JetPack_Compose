package com.macapp.employeemanagement.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.material.color.utilities.MaterialDynamicColors.background
import com.macapp.employeemanagement.R
import com.macapp.employeemanagement.activity.ui.theme.EmployeeManagementTheme
import com.macapp.employeemanagement.activity.ui.theme.PurpleGrey80
import com.macapp.employeemanagement.model_class.login.BottomNavItem
import com.macapp.employeemanagement.screens.MyEmployee
import com.macapp.employeemanagement.screens.MyProfile

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EmployeeManagementTheme {
                BottomNavigation()
            }
        }
    }
}


@Composable
fun BottomNavigation() {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = PurpleGrey80
    systemUiController.setStatusBarColor(statusBarColor)
    val semiBold = FontFamily(Font(R.font.sf_pro_semibold))

    val items = listOf(
        BottomNavItem(
            "My Employees", R.drawable.click, R.drawable.unclick, R.color.outline, R.color.blue
        ), BottomNavItem(
            "My Profile",
            R.drawable.profile_click,
            R.drawable.profile_click,
            R.color.outline,
            R.color.blue
        )
    )

    var selectedIndex by remember { mutableIntStateOf(0) }
    val isVisible = remember { mutableStateOf(true) }

    Scaffold(bottomBar = {
        BottomNavigation(
            backgroundColor = Color.White,
            modifier = Modifier.height(70.dp),
        ) {
            items.forEachIndexed { index, item ->
                BottomNavigationItem(icon = {
                    val iconColor =
                        if (selectedIndex == index) colorResource(id = item.selectedColor) else colorResource(
                            id = item.unselectedColor
                        )
                    val painter: Painter =
                        if (selectedIndex == index) painterResource(id = item.icon) else painterResource(
                            id = item.unselect
                        )
                    Icon(
                        painter,
                        contentDescription = item.title,
                        tint = iconColor,
                        modifier = Modifier
                            .size(34.dp)
                            .padding(bottom = 7.dp)
                    )
                },
                    label = {
                        Text(
                            item.title,
                            color = if (selectedIndex == index) {
                                colorResource(id = R.color.blue)
                            } else {
                                colorResource(id = R.color.outline)
                            },
                            fontFamily = semiBold,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(bottom = 5.dp),
                            letterSpacing = 0.2.sp
                        )
                    },
                    selected = selectedIndex == index,
                    selectedContentColor = colorResource(id = R.color.blue),
                    unselectedContentColor = colorResource(id = R.color.outline),
                    onClick = { selectedIndex = index }

                )
            }
        }
    }

    ) { innerClass ->
        when (selectedIndex) {
            0 -> {
                MyEmployee()
                isVisible.value = true
            }

            1 -> {
                MyProfile()
                isVisible.value = false
            }

            else -> MyEmployee()
        }
        Column(Modifier.padding(innerClass)) {}
    }
}



