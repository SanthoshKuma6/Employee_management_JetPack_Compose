package com.macapp.employeemanagement.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.JsonObject
import com.macapp.employeemanagement.R
import com.macapp.employeemanagement.activity.ui.theme.EmployeeManagementTheme
import com.macapp.employeemanagement.components.Login
import com.macapp.employeemanagement.components.LoginCredential
import com.macapp.employeemanagement.components.MyTextFieldComponent
import com.macapp.employeemanagement.components.NormalEmailText
import com.macapp.employeemanagement.components.NormalTextComponent
import com.macapp.employeemanagement.components.PasswordTextFieldComponent
import com.macapp.employeemanagement.components.TitleImageComponent
import com.macapp.employeemanagement.network.ApiService
import com.macapp.employeemanagement.network.Response
import com.macapp.employeemanagement.network.RetrofitApi
import com.macapp.employeemanagement.preference.DataStoredPreference
import com.macapp.employeemanagement.repository.LoginRepository
import com.macapp.employeemanagement.ui.theme.componentShapes
import com.macapp.employeemanagement.viewmodel.LoginViewModel
import com.macapp.employeemanagement.viewmodel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.log


class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmployeeManagementTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun LoginScreen() {
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(278.dp)
                .background(colorResource(id = R.color.login_background))
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
                .padding(top = 250.dp, start = 16.dp, end = 16.dp)
                .background(Color.White)
                .fillMaxSize()
        ) {
            Column {
                Login(value = stringResource(id = R.string.login))
                Spacer(modifier = Modifier.height(7.dp))
                LoginCredential(value = stringResource(id = R.string.login_enter))
                Spacer(modifier = Modifier.height(20.dp))
                NormalEmailText(value = stringResource(id = R.string.email_text))
                MyTextFieldComponent(labelValue = "Enter your email", onTextSelected = {
                    setEmail(it)

                })
                NormalEmailText(value = stringResource(id = R.string.title_password))
                Spacer(modifier = Modifier.height(10.dp))
                PasswordTextFieldComponent(
                    labelValue = "Enter your password",
                    onTextSelected = {
                        setPassword(it)
                    })
                Spacer(modifier = Modifier.height(0.dp))
                LoginButtonComponent(value = "Login", email, password)

            }
        }
    }

}

@Composable
fun LoginButtonComponent(value: String, email: String, password: String) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val viewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(
            LoginRepository(ApiService.NetworkClient.apiService)
        )
    )

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        onClick = {
            val jsonObject = JsonObject().apply {
                addProperty("email", email)
                addProperty("password", password)
            }
            coroutineScope.launch {
                viewModel.login(jsonObject)

            }
        }

    )
    {
        Box(
            modifier = Modifier
                .background(color = colorResource(id = R.color.blue))
                .fillMaxWidth()
                .height(48.dp), contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )


        }

    }
    val loginState = viewModel.loginState.collectAsStateWithLifecycle()
    when (val result = loginState.value) {
        is Response.Loading -> {}

        is Response.Success -> {
            val loginData = result.data!!.data!!.token
            DataStoredPreference(context).saveUSerData(loginData.toString())
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
            Toast.makeText(context, "Login Successfully", Toast.LENGTH_LONG).show()

        }

        is Response.Error -> {
           Toast.makeText(context,result.errorMessage, Toast.LENGTH_LONG).show()
        }

        else -> {}
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EmployeeManagementTheme {

    }
}