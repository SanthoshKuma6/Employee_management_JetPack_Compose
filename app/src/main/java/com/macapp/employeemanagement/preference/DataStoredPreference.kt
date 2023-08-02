package com.macapp.employeemanagement.preference

import android.content.Context
import android.content.SharedPreferences
import com.macapp.employeemanagement.R

class DataStoredPreference(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)


    companion object{
        const val LOGIN_TOKEN="loginToken"
    }


    fun saveUSerData(loginToken:String){
        val editor=prefs.edit()
        editor.putString(LOGIN_TOKEN,loginToken)
        editor.apply()
    }


    fun getUSerData():HashMap<String,String>{
        val user=HashMap<String,String>()
        user[LOGIN_TOKEN]=prefs.getString(LOGIN_TOKEN,null).toString()

return user
    }
}