package com.macapp.employeemanagement.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.media.browse.MediaBrowser.ConnectionCallback
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.macapp.employeemanagement.R
import com.macapp.employeemanagement.activity.ui.theme.EmployeeManagementTheme
import com.macapp.employeemanagement.components.AddAddressFieldComponent
import com.macapp.employeemanagement.components.AddEmployeeFieldComponent
import com.macapp.employeemanagement.components.DropDownComponent
import com.macapp.employeemanagement.components.NormalEditText
import com.macapp.employeemanagement.network.ApiService
import com.macapp.employeemanagement.network.Response
import com.macapp.employeemanagement.preference.DataStoredPreference
import com.macapp.employeemanagement.repository.LoginRepository
import com.macapp.employeemanagement.viewmodel.LoginViewModel
import com.macapp.employeemanagement.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.util.Calendar

class AddEmployeeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmployeeManagementTheme {
                AddNewEmployee()
            }
        }
    }
}

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
                .padding(start = 0.dp, end = 0.dp)
        ) {
            GreetingSection()


            ////profile image

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.background(colorResource(id = R.color.login_background))
            ) {
                var imageUri by remember {
                    mutableStateOf<Uri?>(null)
                }
                val launcher =
                    rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                        imageUri = uri

                    }
                Image(
                    modifier = Modifier
                        .clickable {
                            launcher.launch("image/*")
                            imageUri?.let { imageUri }

                        }
                        .size(104.dp, 104.dp)
                        .padding(top = 19.dp)
                        .clip(CircleShape),

                    painter = imageUri?.let { rememberImagePainter(it) }
                        ?: painterResource(R.drawable.uploadempty),
                    contentDescription = "image uploader",
                    contentScale = ContentScale.Crop,


                    )
                //field colum
                Column(
                    modifier = Modifier
                        .background(colorResource(id = R.color.login_background))
                        .padding(start = 19.dp, end = 19.dp)
                ) {
                    EmployeeDetails(imageUri)
                }
            }
        }
    }
}

@Composable
fun GreetingSection() {
    val bold = FontFamily(Font(R.font.gothica1_regular))
    val context= LocalContext.current
    val onBackPressedCallback=(context as ComponentActivity).onBackPressedDispatcher
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
                modifier = Modifier.clickable { onBackPressedCallback.onBackPressed() }
            )
            Text(
                text = "Back", modifier = Modifier.clickable { onBackPressedCallback.onBackPressed() }, style = TextStyle(
                    color = colorResource(id = R.color.blue)

                ),
            )
        }

        Text(
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
fun EmployeeDetails(uri: Uri?) {
    val context = LocalContext.current
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
    val (bloodGroup, setBloodGroup) = remember {
        mutableStateOf("")
    }
    val (address, setAddress) = remember {
        mutableStateOf("")
    }
    val selectedDate = remember { mutableStateOf("") }
//    var requestBody = context.contentResolver.openInputStream(uri!!)
//        ?.use { inputStream ->
//            inputStream.readBytes()
//                .toRequestBody("image/*".toMediaTypeOrNull())
//        }
    val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
    builder.addFormDataPart("name", name)
    builder.addFormDataPart("email", email)
    builder.addFormDataPart("department_token", departmentToken)
    builder.addFormDataPart("date_of_birth", selectedDate.value)
    builder.addFormDataPart("mobile_number", number)
    builder.addFormDataPart("address", address)
    builder.addFormDataPart("blood_group", bloodGroup)
//    builder.addFormDataPart("photo", "photoPart", requestBody!!)
    val addEmployee = builder.build()


    NormalEditText(value = stringResource(id = R.string.employee_title))
    AddEmployeeFieldComponent(
        labelValue = stringResource(id = R.string.enter_employee_name),
        onTextSelected = {
            setName(it)
        })

    NormalEditText(value = stringResource(id = R.string.enter_department_name))
    DropDownComponent(
        onTextSelected = {
            setDepartment(it)
        },
    )

    NormalEditText(value = stringResource(id = R.string.email_address))
    AddEmployeeFieldComponent(
        labelValue = stringResource(id = R.string.enter_email_address),
        onTextSelected = {
            setEmail(it)
        })

    NormalEditText(value = stringResource(id = R.string.enter_contact_number_title))
    AddEmployeeFieldComponent(
        labelValue = stringResource(id = R.string.enter_contact_number),
        onTextSelected = {
            setNumber(it)
        })

    NormalEditText(value = stringResource(id = R.string.dob_title))

    DateOutlinedTextField(
        label = "",
        selectedDate = selectedDate.value,
        onDateSelected = { selectedDate.value = it }
    )


    NormalEditText(value = stringResource(id = R.string.blood_group_title))

    AddEmployeeFieldComponent(
        labelValue = stringResource(id = R.string.blood_group),
        onTextSelected = {
            setBloodGroup(it)
        })

    NormalEditText(value = stringResource(id = R.string.address_title))
    AddAddressFieldComponent(
        labelValue = stringResource(id = R.string.address),
        onTextSelected = {
            setAddress(it)
        })
    Spacer(modifier = Modifier.heightIn(min = 20.dp))
    AddEmployeeButton(addEmployee)
    Spacer(modifier = Modifier.heightIn(min = 20.dp))

}


@Composable
fun AddEmployeeButton(requestBody: RequestBody) {
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
                    viewModel.addEmployee(token.toString(), requestBody)
                    Log.d("addEmployee", "AddEmployeeButton: $token,$requestBody")

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
            val intent = Intent(context, MainActivity()::class.java)
            context.startActivity(intent)
            Toast.makeText(context, "${result.data}", Toast.LENGTH_LONG).show()

        }

        is Response.Error -> {
            Toast.makeText(context, "${result.errorMessage}", Toast.LENGTH_LONG).show()
        }

        else -> {}
    }
}

@Composable
fun DateOutlinedTextField(
    label: String,
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (expanded.value) {
        DatePicker(
            onDateSelected = { date ->
                onDateSelected(date)
                expanded.value = false
            },
            selectedDate = selectedDate,
            context = context
        )
    }


    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, colorResource(id = R.color.light_white_text), RoundedCornerShape(4.dp)),
        value = selectedDate,

        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            cursorColor = Color.Black,
            disabledLabelColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent

        ),

        shape = RoundedCornerShape(4.dp),
        onValueChange = onDateSelected,
        trailingIcon = {
            IconButton(
                onClick = { expanded.value = !expanded.value },
            ) {
                Icon(
                    painterResource(id = R.drawable.calender),
                    "Calendar"
                )
            }
        },
        readOnly = false,
        textStyle = TextStyle.Default.copy(fontSize = 14.sp),
        placeholder = { androidx.compose.material.Text(text = "Date of Birth") },

        )


}

@Composable
fun DatePicker(
    onDateSelected: (String) -> Unit,
    selectedDate: String,
    context: Context
) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context, { _, year, monthOfYear, dayOfMonth ->
            val dateType = "" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth
            val parser = SimpleDateFormat("yyyy-MM-dd")
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val output = parser.parse(dateType)?.let { it1 -> formatter.format(it1) }
            if (output != null) {
                onDateSelected(output) // Pass the selected date back to the DateOutlinedTextField
            }
        }, year, month, day
    )
    datePickerDialog.show()
}


