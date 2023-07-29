package com.macapp.employeemanagement.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.macapp.employeemanagement.R
import com.macapp.employeemanagement.components.ButtonComponent
import com.macapp.employeemanagement.components.Login
import com.macapp.employeemanagement.components.LoginCredential
import com.macapp.employeemanagement.components.MyTextFieldComponent
import com.macapp.employeemanagement.components.NormalEmailText
import com.macapp.employeemanagement.components.NormalTextComponent
import com.macapp.employeemanagement.components.PasswordTextFieldComponent
import com.macapp.employeemanagement.components.TitleImageComponent
import com.macapp.employeemanagement.model_class.login.LoginUIEvent
import com.macapp.employeemanagement.network.ApiService
import com.macapp.employeemanagement.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(colorResource(id = R.color.off_white))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TitleImageComponent()
                NormalTextComponent(value = stringResource(id = R.string.login_title))

            }

        }

        Box(
            modifier = Modifier
                .padding(top = 250.dp, start = 15.dp, end = 15.dp)
                .background(Color.White)
                .fillMaxSize()
        ) {
            Column() {

                Login(value = stringResource(id = R.string.login))
                LoginCredential(value = stringResource(id = R.string.login_enter))
                Spacer(modifier = Modifier.height(10.dp))
                NormalEmailText(value = stringResource(id = R.string.email_text))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.email_text),
                    painterResource(id = R.drawable.message), onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                        email=it
//                       username= loginViewModel.onEvent(LoginUIEvent.EmailChanged(it)).toString()

                    },
                )
                NormalEmailText(value = stringResource(id = R.string.title_password))
                Spacer(modifier = Modifier.height(10.dp))
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password_text),
                    painterResource(id = R.drawable.lock),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                        password=it
//                        password= loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it)).toString()
                    },
                    )
                Spacer(modifier = Modifier.height(50.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.login_button),
                    onButtonClicked = {
                        loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)
//                        PostList(email = email, password =password )

                    }
                )


            }
        }


    }


}


@Composable
fun PostList(email:String,password:String) {
    var posts by remember { mutableStateOf(emptyList<LoginUIEvent>()) }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                posts = ApiService.NetworkClient.apiService.login(email, password)
            } catch (e: Exception) {
                // Handle error here
                e.printStackTrace()
            }
        }
    }

}



@Preview
@Composable
fun DefaultLoginScreen() {
    LoginScreen()
}