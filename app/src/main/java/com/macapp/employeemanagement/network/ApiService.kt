package com.macapp.employeemanagement.network

import com.google.gson.JsonObject
import com.macapp.employeemanagement.model_class.login.AddEmployee
import com.macapp.employeemanagement.model_class.login.DepartmentList
import com.macapp.employeemanagement.model_class.login.EmployeeList
import com.macapp.employeemanagement.model_class.login.LoginData
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import java.lang.reflect.Method


interface ApiService {
    @POST("login")
    suspend fun getLogin(@Body jsonObject: JsonObject): Response<LoginData>

    @GET("employee")
    suspend fun getEmployeeList(
        @Header("Authorization") token: String,

        ): Response<EmployeeList>

    @POST("employee")
    suspend fun addEmployee(
        @Header("Authorization") token: String,
        @Body params: RequestBody
    ): Response<AddEmployee>

    @GET("department")
    suspend fun getDepartmentList(
        @Header("Authorization") token: String,

        ): Response<DepartmentList>

    @DELETE("employee/{employeeId}")
    suspend fun deleteEmployeeData(
        @Part("employeeId") employeeId: String,
        @Header("Authorization") token: String,
    ): Response<LoginData>

    @POST("employee/{employeeId}")
    suspend fun updateEmployeeDetails(
        @Path("employeeId") employeeId: String,
        @Query("_method") method: String,
        @Header("Authorization") token: String,
        @Body requestBody: RequestBody
    ): Response<LoginData>


    object NetworkClient {
        private const val BASE_URL = "http://18.218.209.28:8000/api/"

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val apiService: ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }
    }
//    companion object {
//        var apiService: ApiService? = null
//        fun getInstance() : ApiService {
//            if (apiService == null) {
//                apiService = Retrofit.Builder()
//                    .baseUrl("http://18.218.209.28:8000/api/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build().create(ApiService::class.java)
//            }
//            return apiService!!
//        }
//    }
}