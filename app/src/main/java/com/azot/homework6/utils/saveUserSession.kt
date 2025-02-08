package com.azot.homework6.utils

import android.content.Context

fun saveUserSession(context: Context, isLoggedIn: Boolean) {
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putBoolean("is_logged_in", isLoggedIn)
        apply()
    }
}

fun rememberUserSession(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("is_logged_in", false)
}