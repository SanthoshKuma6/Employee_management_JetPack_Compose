package com.macapp.employeemanagement.activity

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.macapp.employeemanagement.R
import com.macapp.employeemanagement.activity.ui.theme.EmployeeManagementTheme
import com.macapp.employeemanagement.app.EmployeeManagement
import com.macapp.employeemanagement.screens.EmployeeDetails
import com.macapp.employeemanagement.viewmodel.LoginViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EmployeeManagementTheme {
               Navigation()
            }
        }
    }
}


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController = navController)
        }
        composable("main_screen") {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
             EmployeeManagementTheme {
                 Login(username = "900", password = "8484848")
                 EmployeeManagement()
             }
            }
        }

    }

}



@Composable
fun Login(username:String,password:String){




}
@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(1f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.3f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )

        )
        delay(300L)
        navController.navigate("main_screen")
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.emma_watson), contentDescription = "logo",
            modifier = Modifier.scale(scale.value)
        )
    }

}




