package com.macapp.employeemanagement.network

import com.macapp.employeemanagement.model_class.login.LoginUIEvent
import com.macapp.employeemanagement.model_class.login.LoginUIState
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("studentlogin.php")
     suspend fun login(
        @Query("registrationNumber") registrationNumber:String,
        @Query("password") password: String
    ): List<LoginUIEvent>


    object NetworkClient {
        private const val BASE_URL = "https://happytestings.com/SRM/php/" // Replace with your API base URL

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
//                    .baseUrl("https://happytestings.com/SRM/php/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build().create(ApiService::class.java)
//            }
//            return apiService!!
//        }
//    }
}