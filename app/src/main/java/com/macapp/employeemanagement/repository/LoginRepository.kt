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

    suspend fun addEmployee(token:String,jsonObject: JsonObject):retrofit2.Response<AddEmployee> {
        return apiService.addEmployee("Bearer $token",jsonObject)

    }

    suspend fun getDepartmentList(token:String):retrofit2.Response<DepartmentList.Data> {
        return apiService.getDepartmentList("Bearer $token")

    }



}