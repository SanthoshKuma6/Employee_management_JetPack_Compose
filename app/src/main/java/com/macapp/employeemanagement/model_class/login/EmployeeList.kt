package com.macapp.employeemanagement.model_class.login


import com.google.gson.annotations.SerializedName
data class EmployeeList(
    @SerializedName("data")
    val `data`: List<Data?>? = listOf()
) {
    data class Data(
        @SerializedName("address")
        val address: String? = "",
        @SerializedName("blood_group")
        val bloodGroup: String? = "",
        @SerializedName("created_at")
        val createdAt: String? = "",
        @SerializedName("date_of_birth")
        val dateOfBirth: String? = "",
        @SerializedName("department_name")
        val departmentName: String? = "",
        @SerializedName("department_token")
        val departmentToken: String? = "",
        @SerializedName("email")
        val email: String? = "",
        @SerializedName("mobile_number")
        val mobileNumber: String? = "",
        @SerializedName("name")
        val name: String? = "",
        @SerializedName("photo")
        val photo: String? = "",
        @SerializedName("token")
        val token: String? = ""
    )
}