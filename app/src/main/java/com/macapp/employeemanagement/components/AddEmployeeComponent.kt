package com.macapp.employeemanagement.components

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.macapp.employeemanagement.R
import com.macapp.employeemanagement.activity.HomeActivity
import com.macapp.employeemanagement.model_class.login.DepartmentList
import com.macapp.employeemanagement.network.ApiService
import com.macapp.employeemanagement.network.Response
import com.macapp.employeemanagement.preference.DataStoredPreference
import com.macapp.employeemanagement.repository.LoginRepository
import com.macapp.employeemanagement.ui.theme.componentShapes
import com.macapp.employeemanagement.viewmodel.LoginViewModel
import com.macapp.employeemanagement.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch
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

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .clip(componentShapes.small),
        placeholder = { Text(text = labelValue) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },


        )
}


@Composable
fun AddAddressFieldComponent(
    labelValue: String, onTextSelected: (String) -> Unit
) {

    val textValue = remember {
        mutableStateOf("")
    }


    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(116.dp)
            .padding(top = 10.dp)
            .clip(componentShapes.small),
        placeholder = { Text(text = labelValue) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },


        )
}

@Composable
fun AdEmployeeButtonComponent() {
    val context = LocalContext.current
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.Blue),
        onClick = {
            val intent = Intent(context, HomeActivity()::class.java)
            context.startActivity(intent)
        },
    ) {
        Text(
            text = "Add Employee",
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }


}


@Composable
fun DropDownComponent(onTextSelected: (String) -> Unit) {
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
            placeholder = { Text(text = "Select your department") },

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





@Composable
fun DatePickerComponent(onTextSelected: (String) -> Unit) {
    val context = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mCalendar = Calendar.getInstance()
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()
    val textValue = remember {
        mutableStateOf("")
    }
    val mDate = remember { mutableStateOf("") }
    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mYear-${mMonth+1}-$mDayOfMonth"
        }, mYear, mMonth, mDay

    )
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        placeholder = { Text(text = "date of birth") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        label = { Text(text = mDate.value)
            onTextSelected(mDate.value)

        },
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(mDate.value)

        },
        trailingIcon = {
            IconButton(onClick = { mDatePickerDialog.show() }) {
                Icon(painter = painterResource(id = R.drawable.calender), contentDescription = "")
            }

        }


    )
}


@Composable
fun BackNavigationComponent(value: String, onTextSelected: (String) -> Unit) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .heightIn(20.dp)
    ) {
        Image(painter = painterResource(id = R.drawable.back), contentDescription = "")
        ClickableText(value = value, onTextSelected)
    }

}


@Composable
fun ClickableText(value: String, onTextSelected: (String) -> Unit) {
    val annotatedString = buildAnnotatedString {
        append(value)
    }


    ClickableText(modifier = Modifier, text = annotatedString, onClick = { offset ->
        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.also { span1 ->
                if ((span1.item == value)) {
                    onTextSelected(span1.item)
                }
            }

    })

}

