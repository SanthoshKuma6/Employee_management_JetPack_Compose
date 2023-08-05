package com.macapp.employeemanagement.repository

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.macapp.employeemanagement.model_class.login.AddEmployee
import com.macapp.employeemanagement.model_class.login.DepartmentList
import com.macapp.employeemanagement.model_class.login.EmployeeList
import com.macapp.employeemanagement.model_class.login.LoginData
import com.macapp.employeemanagement.network.ApiService
import com.macapp.employeemanagement.network.Response
import com.macapp.employeemanagement.network.RetrofitApi
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject


class LoginRepository( val apiService: ApiService) {

    suspend fun loginApi(jsonObject:JsonObject):retrofit2.Response<LoginData> {
        return apiService.getLogin(jsonObject)

    }
    suspend fun getEmployeeList(token:String,):retrofit2.Response<EmployeeList> {
        return apiService.getEmployeeList("Bearer $token")

    }

    suspend fun addEmployee(token: String,requestBody: RequestBody):retrofit2.Response<AddEmployee> {
        return apiService.addEmployee(token,requestBody)

    }

    suspend fun getDepartmentList(token:String):retrofit2.Response<DepartmentList> {
        return apiService.getDepartmentList("Bearer $token")

    }


    suspend fun deleteEmployeeData(token:String,employeeId:String):retrofit2.Response<LoginData> {
        return apiService.deleteEmployeeData(token,employeeId)

    }

    suspend fun updateEmployeeData(employeeToken:String,method:String,token:String,requestBody: RequestBody):retrofit2.Response<LoginData> {
        return apiService.updateEmployeeDetails(employeeToken,method,token,requestBody)

    }



}
