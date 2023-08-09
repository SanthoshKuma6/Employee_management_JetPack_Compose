package com.macapp.employeemanagement.components

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.macapp.employeemanagement.R
import com.macapp.employeemanagement.model_class.login.DepartmentList
import com.macapp.employeemanagement.network.ApiService
import com.macapp.employeemanagement.network.Response
import com.macapp.employeemanagement.preference.DataStoredPreference
import com.macapp.employeemanagement.repository.LoginRepository
import com.macapp.employeemanagement.ui.theme.componentShapes
import com.macapp.employeemanagement.viewmodel.LoginViewModel
import com.macapp.employeemanagement.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun AddEmployeeFieldComponent(
    labelValue: String, onTextSelected: (String) -> Unit
) {

    val textValue = remember {
        mutableStateOf("")
    }
    val regular=FontFamily(Font(R.font.sf_pro_regular))

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .clip(componentShapes.small),
        placeholder = { Text(text = labelValue) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next,),
        singleLine = true, textStyle = TextStyle(fontFamily = regular),
        maxLines = 1,
        value = textValue.value,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.border_color),
            focusedLabelColor = Color.White,
            cursorColor = Color.White,
            backgroundColor = Color.White
        ),
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },


        )
}

@Composable
fun AddEmployeeFieldNumberComponent(
    labelValue: String, onTextSelected: (String) -> Unit
) {

    val textValue = remember {
        mutableStateOf("")
    }
    val regular=FontFamily(Font(R.font.sf_pro_regular))

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .clip(componentShapes.small),
        placeholder = { Text(text = labelValue) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Number),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.border_color),
            focusedLabelColor = Color.White,
            cursorColor = Color.White,
            backgroundColor = Color.White
        ),textStyle = TextStyle(fontFamily = regular),
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },


        )
}
@Composable
fun NormalEditText(value: String) {
    val bold = FontFamily(Font(R.font.gothica1_regular))
    val semiBold = FontFamily(Font(R.font.sf_pro_semibold))

    Text(
        text = value, modifier = Modifier.padding(top = 20.dp), style = TextStyle(
            fontStyle = FontStyle.Normal,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal, fontFamily = bold,
        ), textAlign = TextAlign.Start, fontFamily = semiBold

    )
}

@Composable
fun AddAddressFieldComponent(
    labelValue: String, onTextSelected: (String) -> Unit
) {

    val textValue = remember {
        mutableStateOf("")
    }

    val regular = FontFamily(Font(R.font.gothica1_regular))
    val semiBold = FontFamily(Font(R.font.sf_pro_semibold))
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 10.dp)
            .clip(componentShapes.small),
        placeholder = { Text(text = labelValue) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.border_color),
            focusedLabelColor = Color.White,
            cursorColor = Color.White,
            backgroundColor = Color.White
        ),
        value = textValue.value, textStyle = TextStyle(fontFamily = regular),
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },


        )
}



@Composable
fun DropDownComponent(onTextSelected: (String) -> Unit) {
    val regular=FontFamily(Font(R.font.sf_pro_regular))
    val departmentListItem: ArrayList<DepartmentList.Data?> = ArrayList()
    var boolean by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val viewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(
            LoginRepository(ApiService.NetworkClient.apiService)
        )
    )
    var mExpanded by remember { mutableStateOf(false) }

    var mSelectedText by remember { mutableStateOf("") }
    var mTextFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column() {

        OutlinedTextField(
            value = mSelectedText,
            onValueChange = {
                mSelectedText = it

            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->

                    mTextFieldSize = coordinates.size.toSize()
                },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.border_color),
                focusedLabelColor = Color.White,
                cursorColor = Color.White,
                backgroundColor = Color.White
            ),
            placeholder = { Text(text = "Select your department") },
            textStyle = TextStyle(fontFamily = regular),
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable {
                        val token = DataStoredPreference(context).getUSerData()["loginToken"]

                        if (!boolean) {
                            coroutineScope.launch {
                                viewModel.departmentList(token.toString())
                                boolean = true

                            }
                        }
                        mExpanded = !mExpanded
                    })
            }
        )

        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
        ) {
            departmentListItem.forEach { label ->
                DropdownMenuItem(onClick = {
                    mSelectedText = label?.name.toString()
                    mExpanded = false
                }) {
                    Text(text = label?.name.toString())
                    for (i in departmentListItem) {
                        if (i?.name==mSelectedText){
                            onTextSelected(i.token.toString())
                        }
                    }

                }
            }
        }

    }


    val dropDownState = viewModel.departmentState.collectAsStateWithLifecycle()
    when (val result = dropDownState.value) {
        is Response.Loading -> {}

        is Response.Success -> {
            result.data?.data?.let { departmentListItem.addAll(it) }

        }

        is Response.Error -> {
            val errorMessage = result.errorMessage
            Toast.makeText(context, "$errorMessage", Toast.LENGTH_LONG).show()
        }

        else -> {}
    }
}




