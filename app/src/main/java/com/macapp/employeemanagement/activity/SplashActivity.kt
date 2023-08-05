package com.macapp.employeemanagement.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.macapp.employeemanagement.R
import com.macapp.employeemanagement.activity.ui.theme.EmployeeManagementTheme
import com.macapp.employeemanagement.preference.DataStoredPreference
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmployeeManagementTheme {
                SplashScreen()
            }
        }
    }
}

@Composable
fun SplashScreen() {
//    val systemUiController = rememberSystemUiController()
//    val statusBarColor = background
//    systemUiController.setStatusBarColor(statusBarColor)


    val semiBold = FontFamily(Font(R.font.sf_pro_semibold))
    val context = LocalContext.current

    val loginData=DataStoredPreference(context).getUSerData()["loginToken"]
    LaunchedEffect(key1 = true, ) {
        delay(2000)
        if (loginData=="null" || loginData.isNullOrBlank()) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        } else {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
//    ConstraintLayout(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(colorResource(id = R.color.card_background))
//    ) {
//
//        val myProfile = createRef()
//        val text = createRef()
//
//        Image(
//            painter = painterResource(id = R.drawable.emp_logo),
//            contentDescription = null,
//            modifier = Modifier
//                .constrainAs(myProfile) {
//                    top.linkTo(parent.top)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                    bottom.linkTo(parent.bottom, margin = 50.dp)
//                }
//                .size(90.dp, 90.dp)
//        )
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        androidx.compose.material.Text(
//            text = "Employee Management",
//            color = Color.Black,
//            fontSize = 30.sp,
//            fontFamily = semiBold,
//            letterSpacing = 0.1.sp,
//            modifier = Modifier.constrainAs(text) {
//                top.linkTo(myProfile.bottom, margin = 10.dp)
//                start.linkTo(myProfile.start)
//                end.linkTo(myProfile.end)
//            }
//        )
//    }
}