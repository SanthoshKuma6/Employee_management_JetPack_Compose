package com.macapp.employeemanagement.screens

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.macapp.employeemanagement.R
import com.macapp.employeemanagement.components.AdEmployeeButtonComponent
import com.macapp.employeemanagement.components.AddEmployeeFieldComponent
import com.macapp.employeemanagement.components.ImageComponent
import com.macapp.employeemanagement.components.NormalEmailText

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
                .padding(start = 10.dp, end = 10.dp)
        ) {
            GreetingSection()
            EmployeeDetails()

        }
    }
}

@Composable
fun GreetingSection() {
    val bold=FontFamily(Font(R.font.gothica1_regular))
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
            Icon(
                painter = painterResource(id = R.drawable.back_arrow_24),
                contentDescription = "",
                modifier = Modifier
            )
            Text(
                text = "Back", modifier = Modifier, style = TextStyle(
                    color = colorResource(id = R.color.purple_700)

                )
            )
        }

        androidx.compose.material.Text(
            text = "Add New Employee", modifier = Modifier
                .padding(top = 0.dp)
                .fillMaxWidth()
                .heightIn(min = 0.dp), style = TextStyle(
                fontSize = 24.sp, fontWeight = FontWeight.Normal, fontStyle = FontStyle.Normal
            ), color = Color.Black, textAlign = TextAlign.Center, fontFamily = bold
        )


    }

}


@Composable
fun EmployeeDetails() {

    ImageComponent()
    NormalEmailText(value = stringResource(id = R.string.employee_title))
    AddEmployeeFieldComponent(labelValue = stringResource(id = R.string.enter_employee_name))

    NormalEmailText(value = stringResource(id = R.string.enter_department_name))
    AddEmployeeFieldComponent(labelValue = stringResource(id = R.string.choose_department_name))

    NormalEmailText(value = stringResource(id = R.string.email_address))
    AddEmployeeFieldComponent(labelValue = stringResource(id = R.string.enter_email_address))

    NormalEmailText(value = stringResource(id = R.string.enter_contact_number_title))
    AddEmployeeFieldComponent(labelValue = stringResource(id = R.string.enter_contact_number))

    NormalEmailText(value = stringResource(id = R.string.dob_title))
    AddEmployeeFieldComponent(labelValue = stringResource(id = R.string.dob))

    NormalEmailText(value = stringResource(id = R.string.blood_group_title))
    AddEmployeeFieldComponent(labelValue = stringResource(id = R.string.blood_group))

    NormalEmailText(value = stringResource(id = R.string.address_title))
    AddEmployeeFieldComponent(labelValue = stringResource(id = R.string.address))
    Spacer(modifier = Modifier.heightIn(min = 20.dp))
    AdEmployeeButtonComponent()
    Spacer(modifier = Modifier.heightIn(min = 20.dp))

//    ButtonComponent(
//        value = stringResource(id = R.string.login_button),
//
//        )

}


@Preview
@Composable
fun DefaultAddEmployee() {
    AddNewEmployee()
}