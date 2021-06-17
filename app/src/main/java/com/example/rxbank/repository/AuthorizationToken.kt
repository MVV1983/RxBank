package com.example.rxbank.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.rxbank.R

class AuthorizationToken(applicationContext: Context) {

    private var prefs: SharedPreferences =
        applicationContext.getSharedPreferences(applicationContext.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
    }

    fun saveAuthorizationToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getAuthorizationToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
}