package com.macapp.employeemanagement.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.macapp.employeemanagement.model_class.login.AddEmployee
import com.macapp.employeemanagement.model_class.login.DepartmentList
import com.macapp.employeemanagement.model_class.login.EmployeeList
import com.macapp.employeemanagement.model_class.login.LoginData
import com.macapp.employeemanagement.network.Response
import com.macapp.employeemanagement.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    private val userLogin = MutableStateFlow<Response<LoginData>>(Response.Loading(false))

    val loginState : StateFlow<Response<LoginData>> = userLogin
     fun login(jsonObject: JsonObject) {

        viewModelScope.launch {
            userLogin.value= Response.Loading(true)
            try {
                val data= loginRepository.loginApi(jsonObject)
                if (data.isSuccessful){
                    userLogin.value=Response.Loading(false)
                    userLogin.value=Response.Success(data.body())
                } else{
                    userLogin.value=Response.Loading(false)
                    userLogin.value=Response.Error(data.message())
                }
            }catch (e:Exception){
                Log.d("TAG", "login: ${e.message}")
            }
        }

    }

    private val employeeList=MutableStateFlow<Response<EmployeeList>>(Response.Loading(false))
    val employeeState:StateFlow<Response<EmployeeList>> = employeeList

    fun getEmployeeList(token:String) {

        viewModelScope.launch {
            employeeList.value= Response.Loading(true)
            try {
                val data= loginRepository.getEmployeeList(token)
                if (data.isSuccessful){
                    employeeList.value=Response.Loading(false)
                    employeeList.value=Response.Success(data.body())
                } else{
                    employeeList.value=Response.Loading(false)
                    employeeList.value=Response.Error(data.message())
                }
            }catch (e:Exception){
                Log.d("TAG", "login: ${e.message}")
            }
        }

    }


    private val addEmployeeList=MutableStateFlow<Response<AddEmployee>>(Response.Loading(false))
    val addEmployeeState:StateFlow<Response<AddEmployee>> = addEmployeeList
    fun addEmployee(token: String,requestBody: RequestBody) {

        viewModelScope.launch {
            addEmployeeList.value= Response.Loading(true)
            try {
                val data= loginRepository.addEmployee(token,requestBody)
                if (data.isSuccessful){
                    addEmployeeList.value=Response.Loading(false)
                    addEmployeeList.value=Response.Success(data.body())
                } else{
                    addEmployeeList.value=Response.Loading(false)
                    addEmployeeList.value=Response.Error(data.message())
                }
            }catch (e:Exception){
                Log.d("TAG", "login: ${e.message}")
            }
        }

    }


    private val departmentList=MutableStateFlow<Response<DepartmentList>>(Response.Loading(false))
    val departmentState:StateFlow<Response<DepartmentList>> = departmentList


    fun departmentList(token:String) {

        viewModelScope.launch {
            departmentList.value= Response.Loading(true)
            try {
                val data= loginRepository.getDepartmentList(token)
                if (data.isSuccessful){
                    departmentList.value=Response.Loading(false)
                    departmentList.value=Response.Success(data.body())
                } else{
                    departmentList.value=Response.Loading(false)
                    departmentList.value=Response.Error(data.message())
                }
            }catch (e:Exception){
                Log.d("TAG", "login: ${e.message}")
            }
        }

    }


    private val deleteEmployeeList=MutableStateFlow<Response<LoginData>>(Response.Loading(false))
    val deleteEmployeeState:StateFlow<Response<LoginData>> = deleteEmployeeList

    fun deleteEmployee(token:String,employeeId:String) {

        viewModelScope.launch {
            deleteEmployeeList.value= Response.Loading(true)
            try {
                val data= loginRepository.deleteEmployeeData(token,employeeId)
                if (data.isSuccessful){
                    deleteEmployeeList.value=Response.Loading(false)
                    deleteEmployeeList.value=Response.Success(data.body())
                } else{
                    deleteEmployeeList.value=Response.Loading(false)
                    deleteEmployeeList.value=Response.Error(data.message())
                }
            }catch (e:Exception){
                Log.d("TAG", "login: ${e.message}")
            }
        }

    }
    private val updateEmployeeList=MutableStateFlow<Response<LoginData>>(Response.Loading(false))
    val updateEmployeeState:StateFlow<Response<LoginData>> = updateEmployeeList


    fun updateEmployee(employeeToken:String,method:String,token:String,requestBody: RequestBody) {
        viewModelScope.launch {
            updateEmployeeList.value= Response.Loading(true)
            try {
                val data= loginRepository.updateEmployeeData(employeeToken,method,token,requestBody)
                if (data.isSuccessful){
                    updateEmployeeList.value=Response.Loading(false)
                    updateEmployeeList.value=Response.Success(data.body())
                } else{
                    updateEmployeeList.value=Response.Loading(false)
                    updateEmployeeList.value=Response.Error(data.message())
                }
            }catch (e:Exception){
                Log.d("TAG", "login: ${e.message}")
            }
        }

    }



}

//    var loginUIState = mutableStateOf(LoginUIState())
//    var loginInProcess = mutableStateOf(false)
//    var allValidationPassed = mutableStateOf(false)
//    var TAG = LoginViewModel::class.simpleName
//
//
//    fun onEvent(event: LoginUIEvent) {
//        when (event) {
//            is LoginUIEvent.RegistrationNumberChanged -> {
//                loginUIState.value =
//                    loginUIState.value.copy(registrationNumber = event.registrationNumber)
//                printState()
//            }
//
//            is LoginUIEvent.PasswordChanged -> {
//                loginUIState.value = loginUIState.value.copy(password = event.password)
//                printState()
//            }
//
//            is LoginUIEvent.LoginButtonClicked -> {
//                login()
//            }
//        }
//        validationWithRules()
//    }
//
//
//    private fun validationWithRules() {
//        val registrationNumberResult =
//            Validator.validateEmail(registrationNumber = loginUIState.value.registrationNumber)
//        val passwordResult =
//            Validator.validatePassword(password = loginUIState.value.password)
//
//        loginUIState.value = loginUIState.value.copy(
//            emailError = registrationNumberResult.status,
//            passwordError = passwordResult.status
//        )
//
//        allValidationPassed.value = registrationNumberResult.status && passwordResult.status
//    }
//
//    private fun printState() {
//        Log.d(TAG, "printState:")
//        Log.d(TAG, loginUIState.value.toString())
//
//    }

//    private var loginResponse: List<LoginData.Data> by mutableStateOf(listOf())
//    private var loginResponse: List<LoginData> by mutableStateOf(listOf())
//    private var errorMessage: String by mutableStateOf("Authentication Failed")
//    private val loginRepository by lazy { LoginRepository() }

//    private fun login() {
//
//        loginInProcess.value = true
//        val email = loginUIState.value.registrationNumber
//        val password = loginUIState.value.password
//        val jsonObject = JsonObject().apply {
//            addProperty("email", email)
//            addProperty("password", password)
//        }
//        viewModelScope.launch {
//
//            try {
//               loginRepository.loginApi(jsonObject)
////                loginResponse = data
//                loginInProcess.value = true
//                AppRouter.navigateTo(Screen.MyEmployeeScreen)
//
//            } catch (e: Exception) {
//                loginInProcess.value = false
//                errorMessage = e.message.toString()
//            }
//
//        }
//
//
//    }
//
//}







//admin@gmail.com
//Admin@2022