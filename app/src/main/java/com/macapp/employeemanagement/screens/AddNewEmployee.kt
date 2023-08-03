package com.macapp.employeemanagement.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.JsonObject
import com.macapp.employeemanagement.R
import com.macapp.employeemanagement.activity.HomeActivity
import com.macapp.employeemanagement.components.AddAddressFieldComponent
import com.macapp.employeemanagement.components.AddEmployeeFieldComponent
import com.macapp.employeemanagement.components.DatePickerComponent
import com.macapp.employeemanagement.components.DropDownComponent
import com.macapp.employeemanagement.components.ImageComponent
import com.macapp.employeemanagement.components.NormalEmailText
import com.macapp.employeemanagement.network.ApiService
import com.macapp.employeemanagement.network.Response
import com.macapp.employeemanagement.preference.DataStoredPreference
import com.macapp.employeemanagement.repository.LoginRepository
import com.macapp.employeemanagement.viewmodel.LoginViewModel
import com.macapp.employeemanagement.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun AddNewEmployee() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp)
        ) {
            GreetingSection()
            EmployeeDetails()

        }
    }
}

@Composable
fun GreetingSection() {
    val bold = FontFamily(Font(R.font.gothica1_regular))
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(0.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "",
                modifier = Modifier
            )
            Text(
                text = "Back", modifier = Modifier, style = TextStyle(
                    color = colorResource(id = R.color.blue)

                )
            )
        }

        androidx.compose.material.Text(
            text = "Add New Employee", modifier = Modifier
                .padding(top = 0.dp)
                .fillMaxWidth()
                .heightIn(min = 0.dp), style = TextStyle(
                fontSize = 17.sp, fontWeight = FontWeight.Normal, fontStyle = FontStyle.Normal
            ), color = Color.Black, textAlign = TextAlign.Center, fontFamily = bold
        )


    }

}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun EmployeeDetails() {
    val context = LocalContext.current
    val boolean by remember {
        mutableStateOf(false)
    }
    val (name, setName) = remember { mutableStateOf("") }
    val (departmentToken, setDepartment) = remember {
        mutableStateOf("")
    }
    val (email, setEmail) = remember {
        mutableStateOf("")
    }
    val (number, setNumber) = remember {
        mutableStateOf("")
    }
    val (dateOfBirth, setDateOfBirth) = remember {
        mutableStateOf("")
    }
    val (bloodGroup, setBloodGroup) = remember {
        mutableStateOf("")
    }
    val (address, setAddress) = remember {
        mutableStateOf("")
    }
    val token = DataStoredPreference(context).getUSerData()["loginToken"]

    val jsonObject = JsonObject().apply {
        addProperty("name", name)
        addProperty("email", email)
        addProperty("department_token", departmentToken)
        addProperty("date_of_birth", dateOfBirth)
        addProperty("mobile_number", number)
        addProperty("address", address)
        addProperty("blood_group", bloodGroup)
    }

    ImageComponent()
    NormalEmailText(value = stringResource(id = R.string.employee_title))
    AddEmployeeFieldComponent(
        labelValue = stringResource(id = R.string.enter_employee_name),
        onTextSelected = {
            setName(it)
        })

    NormalEmailText(value = stringResource(id = R.string.enter_department_name))
    DropDownComponent(
        onTextSelected = {
            setDepartment(it)
        },
    )

    NormalEmailText(value = stringResource(id = R.string.email_address))
    AddEmployeeFieldComponent(
        labelValue = stringResource(id = R.string.enter_email_address),
        onTextSelected = {
            setEmail(it)
        })

    NormalEmailText(value = stringResource(id = R.string.enter_contact_number_title))
    AddEmployeeFieldComponent(
        labelValue = stringResource(id = R.string.enter_contact_number),
        onTextSelected = {
            setNumber(it)
        })

    NormalEmailText(value = stringResource(id = R.string.dob_title))
    DatePickerComponent(onTextSelected = {
        setDateOfBirth(it)
    })
    NormalEmailText(value = stringResource(id = R.string.blood_group_title))

    AddEmployeeFieldComponent(
        labelValue = stringResource(id = R.string.blood_group),
        onTextSelected = {
            setBloodGroup(it)
        })

    NormalEmailText(value = stringResource(id = R.string.address_title))
    AddAddressFieldComponent(labelValue = stringResource(id = R.string.address), onTextSelected = {
        setAddress(it)
    })
    Spacer(modifier = Modifier.heightIn(min = 20.dp))
    AddEmployeeButton(jsonObject)
    Spacer(modifier = Modifier.heightIn(min = 20.dp))
//if (!boolean){
//    coroutineScope.launch {
//        val token=DataStoredPreference(context).getUSerData()["loginToken"]
//        viewModel.getEmployeeList(token.toString())
//    }
//}

}

@Composable
fun AddEmployeeButton(jsonObject: JsonObject) {
    val coroutineScope = rememberCoroutineScope()
    val apiHit by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val viewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(
            LoginRepository(ApiService.NetworkClient.apiService)
        )
    )
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.Blue),
        onClick = {
            val token = DataStoredPreference(context).getUSerData()["loginToken"]

            if (!apiHit) {
                coroutineScope.launch {
                    viewModel.addEmployee(token.toString(), jsonObject)
                    Log.d("addDetails", "AddEmployeeButton: $jsonObject")
                }

            }

        },
    ) {
        androidx.compose.material.Text(
            text = "Add Employee",
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }

    val employeeState = viewModel.addEmployeeState.collectAsStateWithLifecycle()
    when (val result = employeeState.value) {
        is Response.Loading -> {}

        is Response.Success -> {
            val intent = Intent(context, HomeActivity()::class.java)
            context.startActivity(intent)
            Toast.makeText(context, "${result.data}", Toast.LENGTH_LONG).show()

        }

        is Response.Error -> {
            Toast.makeText(context, "${result.errorMessage}", Toast.LENGTH_LONG).show()
        }

        else -> {}
    }
}

@Preview
@Composable
fun DefaultAddEmployee() {
    AddNewEmployee()
}