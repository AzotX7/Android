package com.azot.homework6.utils

import android.content.Context

fun registerUser(context: Context, username: String, password: String): Boolean {
    val sharedPreferences = context.getSharedPreferences("users", Context.MODE_PRIVATE)
    if (sharedPreferences.contains(username)) {
        return false
    } else {
        sharedPreferences.edit().putString(username, password).apply()
        return true
    }
}


fun validateUser(context: Context, username: String, password: String): Boolean {
    val sharedPreferences = context.getSharedPreferences("users", Context.MODE_PRIVATE)
    val storedPassword = sharedPreferences.getString(username, null)
    return storedPassword != null && storedPassword == password
}