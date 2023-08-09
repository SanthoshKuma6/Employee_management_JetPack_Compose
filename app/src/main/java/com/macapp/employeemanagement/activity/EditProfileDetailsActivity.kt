package com.macapp.employeemanagement.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.macapp.employeemanagement.R
import com.macapp.employeemanagement.activity.ui.theme.PurpleGrey80
import com.macapp.employeemanagement.model_class.login.DepartmentList
import com.macapp.employeemanagement.network.ApiService
import com.macapp.employeemanagement.network.Response
import com.macapp.employeemanagement.preference.DataStoredPreference
import com.macapp.employeemanagement.repository.LoginRepository
import com.macapp.employeemanagement.ui.theme.EmployeeManagementTheme
import com.macapp.employeemanagement.viewmodel.LoginViewModel
import com.macapp.employeemanagement.viewmodel.ViewModelFactory
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.util.Calendar

class EditProfileDetailsActivity : ComponentActivity() {
    private val departmentListData: ArrayList<DepartmentList.Data?> by lazy { arrayListOf() }
    private val departmentName: ArrayList<String> by lazy { arrayListOf() }
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmployeeManagementTheme {
                val systemUiController = rememberSystemUiController()
                var serverCall by remember {
                    mutableStateOf(false)
                }
                val coroutineScope = rememberCoroutineScope()

                val statusBarColor = colorResource(id = R.color.white)
                systemUiController.setStatusBarColor(statusBarColor)
                val viewModel: LoginViewModel = viewModel(
                    factory = ViewModelFactory(
                        LoginRepository(ApiService.NetworkClient.apiService)
                    )
                )
                val loginToken = DataStoredPreference(this).getUSerData()["loginToken"]

                val intent = intent

                if (!serverCall){
                    coroutineScope.launch {
                        viewModel.departmentList(loginToken.toString())
                        serverCall= true
                    }

                }
                val dropDownState = viewModel.departmentState.collectAsStateWithLifecycle()
                when (val result = dropDownState.value) {
                    is Response.Loading -> {}

                    is Response.Success -> {
                        result.data?.data?.let { departmentListData.addAll(it) }
                        for (i in result.data?.data!!) {
                            i?.name?.let { it1 ->
                                departmentName.add(it1)
                            }

                        }
                    }

                    is Response.Error -> {
                        val errorMessage = result.errorMessage
                        Toast.makeText(this, "$errorMessage", Toast.LENGTH_LONG).show()
                    }

                    else -> {}
                }
                if (intent.hasExtra("employeeList")) {
                    EmployeeEditDetails(departmentListData, departmentName, intent)
                }
                EmployeeEditDetails(departmentListData, departmentName, intent)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun EmployeeEditDetails(
    departmentListData: ArrayList<DepartmentList.Data?>,
    departmentName: ArrayList<String>,
    intent: Intent,
) {

    val semiBold = FontFamily(Font(R.font.sf_pro_semibold))
    val bold = FontFamily(Font(R.font.sf_pro_bold))
    val regular = FontFamily(Font(R.font.sf_pro_regular))
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val onBackPressedCallback = (context as ComponentActivity).onBackPressedDispatcher
    val viewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(
            LoginRepository(ApiService.NetworkClient.apiService)
        )
    )
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    val loginToken = DataStoredPreference(context).getUSerData()["loginToken"]

    val employeeName = intent.extras?.getString("name").toString()
    val department = intent.extras?.getString("department").toString()
    val emailAddress = intent.extras?.getString("email").toString()
    val employeeNumber = intent.extras?.getString("number").toString()
    val dateOfBirth = intent.extras?.getString("dob").toString()
    val employeeBloodGroup = intent.extras?.getString("blood").toString()
    val employeeAddress = intent.extras?.getString("address").toString()
    val image = intent.extras?.getString("image").toString()
    val token = intent.extras?.getString("token").toString()


    val (name, setName) = remember { mutableStateOf(employeeName) }
    val (email, setEmail) = remember { mutableStateOf(emailAddress) }
    val (number, setNumber) = remember { mutableStateOf(employeeNumber) }
    val (bloodGroup, setBloodGroup) = remember { mutableStateOf(employeeBloodGroup) }
    val (address, setAddress) = remember { mutableStateOf(employeeAddress) }
    val selectedDepartment = remember { mutableStateOf(department) }
    val selectedDateOfBirth = remember { mutableStateOf(dateOfBirth) }
    val departmentToken = remember { mutableStateOf("") }

    Log.d("mydep", "EmployeeEditDetails: $selectedDepartment")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 18.dp)
            .verticalScroll(rememberScrollState())
            .background(colorResource(id = R.color.login_background))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    colorResource(id = R.color.white)
                )
        ) {
            Image(
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .clickable { onBackPressedCallback.onBackPressed() },
                painter = painterResource(id = R.drawable.back),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(5.dp))
            androidx.compose.material.Text(text = "Back",
                color = colorResource(id = R.color.blue),
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .clickable {
                        onBackPressedCallback.onBackPressed()
                    })
            androidx.compose.material3.Text(
                text = "Edit Employee",
                fontSize = 16.sp,
                fontFamily = semiBold,
                color = Color.Black,
                modifier = Modifier.padding(start = 50.dp, top = 10.dp, bottom = 15.dp)
            )


        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.background(colorResource(id = R.color.card_background))
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = imageUri?.let { rememberAsyncImagePainter(it) }
                    ?: image?.let { rememberAsyncImagePainter(it) }
                    ?: painterResource(R.drawable.uploadempty),
                contentDescription = "Upload Image",
                modifier = Modifier
                    .size(width = 104.dp, height = 104.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        launcher.launch("image/*")
                    },

                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(30.dp))

            androidx.compose.material3.Text(
                text = "Employee Name",
                fontSize = 12.sp,
                fontFamily = semiBold,
                color = Color.Black,
                modifier = Modifier.padding(start = 20.dp)
            )


            ///NAME
            OutlinedTextField(
                value = name,
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                placeholder = {
                    androidx.compose.material3.Text(
                        "Enter Employee Name",
                        fontFamily = regular,
                        fontSize = 16.sp,
                        letterSpacing = 0.1.sp,
                        color = colorResource(id = R.color.placeholer),
                        textAlign = TextAlign.Start
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 5.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = setName,
                colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedBorderColor = colorResource(id = R.color.divider_color),
                    unfocusedBorderColor = colorResource(id = R.color.divider_color)
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            androidx.compose.material3.Text(
                text = "Department",
                fontSize = 12.sp,
                fontFamily = semiBold,
                color = Color.Black,
                modifier = Modifier.padding(start = 20.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp)
            ) {

                //DEPARTMENT
                EditDropdownOutlinedTextField(
                    label = "",
                    items = departmentName,
                    selectedItem = selectedDepartment.value,
                    onValueChange = {
                        selectedDepartment.value = it
                    }

                )


            }
            Spacer(modifier = Modifier.height(10.dp))
            androidx.compose.material3.Text(
                text = "Email Address",
                fontSize = 12.sp,
                fontFamily = semiBold,
                color = Color.Black,
                modifier = Modifier.padding(start = 20.dp)
            )


            //EMAIL
            OutlinedTextField(
                value = email,
                singleLine = true,
                placeholder = {
                    androidx.compose.material3.Text(
                        "Enter Email Address",
                        fontFamily = regular,
                        fontSize = 16.sp,
                        letterSpacing = 0.1.sp,
                        color = colorResource(id = R.color.placeholer),
                        textAlign = TextAlign.Start
                    )
                },
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 5.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = setEmail,
                colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedBorderColor = colorResource(id = R.color.border_color),
                    unfocusedBorderColor = colorResource(id = R.color.border_color)
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            androidx.compose.material3.Text(
                text = "Contact Number",
                fontSize = 12.sp,
                fontFamily = semiBold,
                color = Color.Black,
                modifier = Modifier.padding(start = 20.dp)
            )


            //NUMBER
            OutlinedTextField(
                value = number,
                singleLine = true,
                placeholder = {
                    androidx.compose.material3.Text(
                        "Enter Contact Number",
                        fontFamily = regular,
                        fontSize = 16.sp,
                        letterSpacing = 0.1.sp,
                        color = colorResource(id = R.color.placeholer),
                        textAlign = TextAlign.Start
                    )
                },
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 5.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = setNumber,
                colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedBorderColor = colorResource(id = R.color.border_color),
                    unfocusedBorderColor = colorResource(id = R.color.border_color)
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            androidx.compose.material3.Text(
                text = "Date of birth",
                fontSize = 12.sp,
                fontFamily = semiBold,
                color = Color.Black,
                modifier = Modifier.padding(start = 20.dp)
            )


            //DOB
            DateOutlinedEditTextField(
                label = "",
                selectedDate = selectedDateOfBirth.value,
                onDateSelected = { selectedDateOfBirth.value = it }

            )
            Spacer(modifier = Modifier.height(10.dp))
            androidx.compose.material3.Text(
                text = "Blood Group",
                fontSize = 12.sp,
                fontFamily = semiBold,
                color = Color.Black,
                modifier = Modifier.padding(start = 20.dp)
            )


            //BLOOD GROUP
            OutlinedTextField(
                value = bloodGroup,
                singleLine = true,
                placeholder = {
                    androidx.compose.material3.Text(
                        "Enter Blood Group",
                        fontFamily = regular,
                        fontSize = 16.sp,
                        letterSpacing = 0.1.sp,
                        color = colorResource(id = R.color.placeholer),
                        textAlign = TextAlign.Start
                    )
                },
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 5.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = setBloodGroup,
                colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedBorderColor = colorResource(id = R.color.border_color),
                    unfocusedBorderColor = colorResource(id = R.color.border_color)
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            androidx.compose.material3.Text(
                text = "Address",
                fontSize = 12.sp,
                fontFamily = semiBold,
                color = Color.Black,
                modifier = Modifier.padding(start = 20.dp)
            )

            //ADDRESS
            OutlinedTextField(
                value = address,
                singleLine = true,
                placeholder = {
                    androidx.compose.material3.Text(
                        "Enter Address",
                        fontFamily = regular,
                        fontSize = 16.sp,
                        letterSpacing = 0.1.sp,
                        color = colorResource(id = R.color.placeholer),
                        textAlign = TextAlign.Start
                    )
                },
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 5.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = setAddress,
                colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedBorderColor = colorResource(id = R.color.border_color),
                    unfocusedBorderColor = colorResource(id = R.color.border_color)
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            val buttonClicked = remember { mutableStateOf(false) }
//            departmentListData.map {
//                if (selectedDepartment.value == it?.name) {
//                    departmentToken.value = it.token.toString()
//
//                }
//            }
            androidx.compose.material3.Button(
                onClick = {
                    for (i in departmentListData){
                        if (i?.name==selectedDepartment.value){
                            departmentToken.value=(i.token.toString())
                        }
                        Log.d(
                            "selectedDepartment",
                            "EmployeeEditDetails: ${selectedDepartment.value}"
                        )

                    }

                    departmentToken.value
                    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+.+[a-z]+"
                    if (name.isEmpty() && email.isEmpty() && employeeNumber.isEmpty() && employeeBloodGroup.isEmpty() && address.isEmpty()) {
                        Toast.makeText(context, "Required Field is Empty", Toast.LENGTH_SHORT)
                            .show()
                    } else if (email.isEmpty()) {
                        Toast.makeText(context, "Enter your email address", Toast.LENGTH_SHORT)
                            .show()
                    } else if (!email.matches(emailPattern.toRegex())) {
                        Toast.makeText(
                            context,
                            "Please Check your  Email Address",
                            Toast.LENGTH_SHORT
                        )

                            .show()
                    } else {
                        buttonClicked.value = true
                        if (imageUri == null) {
                            val file = image.substringAfterLast("/")
                            Log.d("Editable", "Edit: ${departmentToken.value}")

                            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
                            builder.addFormDataPart("name", name)
                            builder.addFormDataPart("email", email)
                            builder.addFormDataPart("department_token", departmentToken.value)
                            builder.addFormDataPart("date_of_birth", selectedDateOfBirth.value)
                            builder.addFormDataPart("mobile_number", number)
                            builder.addFormDataPart("address", address)
                            builder.addFormDataPart("blood_group", bloodGroup)
                            builder.addFormDataPart("token", token)
                            builder.addFormDataPart("photo", file)
                            val addEmployeeData = builder.build()
                            coroutineScope.launch {
                                viewModel.updateEmployee(
                                    token,
                                    "PATCH",
                                    loginToken.toString(),
                                    addEmployeeData
                                )
                                Log.d(
                                    "tag",
                                    "EmployeeEditDetails: $token ${loginToken.toString()}  $addEmployeeData"
                                )

                            }

                        } else {
                            val requestBody = context.contentResolver.openInputStream(imageUri!!)
                                ?.use { inputStream ->
                                    inputStream.readBytes()
                                        .toRequestBody("image/*".toMediaTypeOrNull())
                                }
                            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
                            builder.addFormDataPart("name", name)
                            builder.addFormDataPart("email", email)
                            builder.addFormDataPart("department_token", departmentToken.value)
                            builder.addFormDataPart("date_of_birth", selectedDateOfBirth.value)
                            builder.addFormDataPart("mobile_number", number)
                            builder.addFormDataPart("address", address)
                            builder.addFormDataPart("blood_group", bloodGroup)
                            builder.addFormDataPart("token", token)
                            builder.addFormDataPart("photo", "jpg", requestBody!!)
                            val addEmployeeData = builder.build()
                            coroutineScope.launch {
                                viewModel.updateEmployee(
                                    token,
                                    "PATCH",
                                    loginToken.toString(),
                                    addEmployeeData
                                )
                                Log.d(
                                    "tag",
                                    "EmployeeEditDetails: $token ${loginToken.toString()}  $addEmployeeData"
                                )

                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 5.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue)),
                shape = RoundedCornerShape(4.dp)
            ) {
                androidx.compose.material3.Text(
                    text = "Edit Employee",
                    color = colorResource(id = R.color.white),
                    fontSize = 16.sp,
                    fontFamily = bold
                )
            }
            Spacer(modifier = Modifier.height(30.dp))

        }
        val updateState = viewModel.updateEmployeeState.collectAsStateWithLifecycle()
        when (val result = updateState.value) {
            is Response.Loading -> {}

            is Response.Success -> {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }

            is Response.Error -> {
                Toast.makeText(context, result.errorMessage, Toast.LENGTH_LONG).show()

            }
        }


    }

}


@Composable
fun DateOutlinedEditTextField(
    label: String, selectedDate: String, onDateSelected: (String) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (expanded.value) {
        EditDatePicker(
            onDateSelected = { date ->
                onDateSelected(date) // Pass the selected date back to the DateOutlinedTextField
                expanded.value = false
            }, selectedDate = selectedDate, context = context
        )
    }


    TextField(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .border(1.dp, colorResource(id = R.color.border_color), RoundedCornerShape(4.dp)),
        value = selectedDate,

        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            cursorColor = Color.Black,
            disabledLabelColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,


            ),

        shape = RoundedCornerShape(4.dp),
        onValueChange = onDateSelected,
        trailingIcon = {
            IconButton(
                onClick = { expanded.value = !expanded.value },
            ) {
                Icon(
                    painterResource(id = R.drawable.calender), "Calendar"
                )
            }
        },
        readOnly = false,
        textStyle = TextStyle.Default.copy(fontSize = 14.sp),
        placeholder = { androidx.compose.material.Text(text = "Date of Birth") },

        )


}


@Composable
fun EditDropdownOutlinedTextField(
    label: String,
    items: List<String>,
    selectedItem: String,
    onValueChange: (String) -> Unit
) {
    val expanded = remember { mutableStateOf(value = false) }
    val context = LocalContext.current
    var boolean by remember {
        mutableStateOf(false)
    }
    val viewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(
            LoginRepository(ApiService.NetworkClient.apiService)
        )
    )

    val coroutineScope = rememberCoroutineScope()
    androidx.compose.material.TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 6.dp, end = 6.dp, top = 5.dp)
            .border(1.dp, colorResource(id = R.color.divider_color), RoundedCornerShape(4.dp)),
        value = selectedItem,
        colors = androidx.compose.material.TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            cursorColor = Color.Black,
            disabledLabelColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent

        ),
        shape = RoundedCornerShape(4.dp),
        onValueChange = onValueChange,
        trailingIcon = {
            androidx.compose.material.IconButton(onClick = {

                val token = DataStoredPreference(context).getUSerData()["loginToken"]
                if (!boolean) {
                    coroutineScope.launch {
                        viewModel.departmentList(token.toString())
                        boolean = true

                    }
                }

                expanded.value = !expanded.value
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.dropdown),
                    "selected",
                    Modifier.rotate(
                        if (expanded.value)
                            180f
                        else
                            360f
                    )
                )
            }
        },
        readOnly = true,
        textStyle = TextStyle.Default.copy(fontSize = 14.sp)
    )


    DropdownMenu(
        expanded = expanded.value,
        modifier = Modifier
            .width(350.dp)
            .padding(20.dp),
        onDismissRequest = { expanded.value = false }
    ) {
        items.forEachIndexed { _, item ->
            DropdownMenuItem(onClick = {
                onValueChange(item)
                expanded.value = false
            }) {
                androidx.compose.material3.Text(item)
            }
        }
    }
}

@Composable
fun EditDatePicker(
    onDateSelected: (String) -> Unit, selectedDate: String, context: Context
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

