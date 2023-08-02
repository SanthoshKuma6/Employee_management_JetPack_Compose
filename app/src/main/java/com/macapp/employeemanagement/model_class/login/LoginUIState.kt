package com.macapp.employeemanagement.model_class.login

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class LoginUIState(
    var registrationNumber: String="",
    var password: String = "",

    var emailError: Boolean = false,
    var passwordError: Boolean = false,
)



