package com.macapp.employeemanagement.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Paint.Style
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
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.macapp.employeemanagement.R
import com.macapp.employeemanagement.activity.ui.theme.EmployeeManagementTheme
import com.macapp.employeemanagement.activity.ui.theme.PurpleGrey80
import com.macapp.employeemanagement.components.AddAddressFieldComponent
import com.macapp.employeemanagement.components.AddEmployeeFieldComponent
import com.macapp.employeemanagement.components.AddEmployeeFieldNumberComponent
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
                val systemUiController = rememberSystemUiController()
                val statusBarColor = PurpleGrey80
                systemUiController.setStatusBarColor(statusBarColor)
                AddNewEmployee()
            }
        }
    }
}

@Composable
fun AddNewEmployee() {


    val systemUiController = rememberSystemUiController()
    val statusBarColor = PurpleGrey80 // Change this to the desired color

    SideEffect {
        systemUiController.setStatusBarColor(statusBarColor)
    }
//    val systemUiController = rememberSystemUiController()
//    val statusBarColor = PurpleGrey80
//    systemUiController.setStatusBarColor(statusBarColor)
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
                Spacer(modifier = Modifier.height(30.dp))
                Image(
                    modifier = Modifier
                        .clickable {
                            launcher.launch("image/*")
                            imageUri?.let { imageUri }

                        }
                        .size(width = 104.dp, height = 104.dp)
                        .padding(top = 0.dp)
                        .fillMaxSize()
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
    val systemUiController = rememberSystemUiController()
    val statusBarColor = PurpleGrey80
    systemUiController.setStatusBarColor(statusBarColor)
    val bold = FontFamily(Font(R.font.gothica1_regular))
    val semi_bold = FontFamily(Font(R.font.sf_pro_semibold))
    val context = LocalContext.current
    val onBackPressedCallback = (context as ComponentActivity).onBackPressedDispatcher
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
                text = "Back",
                modifier = Modifier.clickable { onBackPressedCallback.onBackPressed() },
                style = TextStyle(
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
            ), color = colorResource(id = R.color.black), textAlign = TextAlign.Center, fontFamily = semi_bold
        )


    }
}


@SuppressLint("CoroutineCreationDuringComposition", "UnrememberedMutableState")
@Composable
fun EmployeeDetails(uri: Uri?) {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = PurpleGrey80
    systemUiController.setStatusBarColor(statusBarColor)
    val coroutineScope = rememberCoroutineScope()
    val apiHit by remember {
        mutableStateOf(false)
    }
    val viewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(
            LoginRepository(ApiService.NetworkClient.apiService)
        )
    )
    val buttonClick= mutableStateOf(false)
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




    NormalEditText(value = stringResource(id = R.string.employee_title))
    AddEmployeeFieldComponent(
        labelValue = stringResource(id = R.string.enter_employee_name),
        onTextSelected = {
            setName(it)
        })

    NormalEditText(value = stringResource(id = R.string.enter_department_name))
    Spacer(modifier = Modifier.heightIn(min = 12.dp))

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
    AddEmployeeFieldNumberComponent(
        labelValue = stringResource(id = R.string.enter_contact_number),
        onTextSelected = {
            setNumber(it)
        })

    NormalEditText(value = stringResource(id = R.string.dob_title))
    Spacer(modifier = Modifier.heightIn(min = 12.dp))

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
    Spacer(modifier = Modifier.heightIn(min = 42.dp))
//    AddEmployeeButton(addEmployee)

    Button(modifier = Modifier
        .fillMaxWidth()
        .height(48.dp), colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue))
       , onClick = {
        val token = DataStoredPreference(context).getUSerData()["loginToken"]
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+.+[a-z]+"
        if (email.isEmpty()) {
            Toast.makeText(context, "email is should not be Empty", Toast.LENGTH_LONG).show()
        }
        else if (!email.matches(emailPattern.toRegex())){
            Toast.makeText(context, "email is incorrect", Toast.LENGTH_LONG).show()

        }else if (number.isEmpty()){
            Toast.makeText(context, "email should not be Empty", Toast.LENGTH_LONG).show()

        } else if (number.length<=9){
            Toast.makeText(context,"Contact number is must 10 digit",Toast.LENGTH_LONG).show()
        } else if (name.isEmpty()){
            Toast.makeText(context, "name should not be Empty", Toast.LENGTH_LONG).show()

        }

                val requestBody = context.contentResolver.openInputStream(uri!!)
                    ?.use { inputStream ->
                        inputStream.readBytes()
                            .toRequestBody("image/*".toMediaTypeOrNull())
                    }
                buttonClick.value=true
                val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
                builder.addFormDataPart("name", name)
                builder.addFormDataPart("email", email)
                builder.addFormDataPart("department_token", departmentToken)
                builder.addFormDataPart("date_of_birth", selectedDate.value)
                builder.addFormDataPart("mobile_number", number)
                builder.addFormDataPart("address", address)
                builder.addFormDataPart("blood_group", bloodGroup)
                builder.addFormDataPart("photo", "photoPart", requestBody!!)
                val addEmployee = builder.build()
                if (!apiHit) {
                    coroutineScope.launch {
                        viewModel.addEmployee(token.toString(), addEmployee)
                        Log.d("addEmployee", "AddEmployeeButton: $token,$addEmployee")

                    }
        }
    }) {
            androidx.compose.material.Text(
                text = "Add Employee",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
    }
    Spacer(modifier = Modifier.heightIn(min = 60.dp))
    val employeeState = viewModel.addEmployeeState.collectAsStateWithLifecycle()
    when (val result = employeeState.value) {
        is Response.Loading -> {

        }

        is Response.Success -> {
            val intent = Intent(context, MainActivity()::class.java)
            context.startActivity(intent)
            Toast.makeText(context, "${result.data}", Toast.LENGTH_LONG).show()
            Toast.makeText(context, "Successfully Added", Toast.LENGTH_LONG).show()

        }

        is Response.Error -> {
            Toast.makeText(context, "${result.errorMessage}", Toast.LENGTH_LONG).show()
        }

        else -> {}
    }
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

        },
    ) {
        androidx.compose.material.Text(
            text = "Add Employee",
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
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
    val regular=FontFamily(Font(R.font.sf_pro_regular))

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
            .border(1.dp, colorResource(id = R.color.border_color), RoundedCornerShape(4.dp)),
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


