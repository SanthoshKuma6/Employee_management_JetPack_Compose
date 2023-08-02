package com.macapp.employeemanagement.model_class.login


import com.google.gson.annotations.SerializedName
data class LoginData(
    @SerializedName("data")
    val `data`: Data? = Data(),
    @SerializedName("message")
    val message: Any? = Any(),
    @SerializedName("status")
    val status: String? = ""
) {
    data class Data(
        @SerializedName("message")
        val message: String? = "",
        @SerializedName("name")
        val name: String? = "",
        @SerializedName("token")
        val token: String? = ""
    )
}