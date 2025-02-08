package com.azot.homework6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.azot.homework6.screens.AddSneakerScreen
import com.azot.homework6.screens.HomeScreen
import com.azot.homework6.screens.LoginScreen
import com.azot.homework6.screens.RegisterScreen
import com.azot.homework6.utils.registerUser
import com.azot.homework6.utils.saveUserSession
import com.azot.homework6.utils.rememberUserSession
import com.azot.homework6.utils.validateUser

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = applicationContext
            var isLoggedIn by remember { mutableStateOf(rememberUserSession(context)) }
            val navController = rememberNavController()

            LaunchedEffect(isLoggedIn) {
                if (isLoggedIn) {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                        popUpTo("register") { inclusive = true }
                    }
                }
            }

            NavHost(
                navController = navController,
                startDestination = if (isLoggedIn) "home" else "login"
            ) {
                composable("login") {
                    LoginScreen(
                        onLogin = { email, password, ctx ->
                            when {
                                email.isBlank() || password.isBlank() -> {
                                    android.widget.Toast.makeText(
                                        ctx,
                                        "Введите данные",
                                        android.widget.Toast.LENGTH_SHORT
                                    ).show()
                                }

                                !validateUser(ctx, email, password) -> {
                                    android.widget.Toast.makeText(
                                        ctx,
                                        "Пользователь не найден, сначала зарегистрируйтесь",
                                        android.widget.Toast.LENGTH_SHORT
                                    ).show()
                                }

                                else -> {
                                    saveUserSession(ctx, true)
                                    isLoggedIn = true
                                    navController.navigate("home") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            }
                        },
                        onRegister = { navController.navigate("register") },
                        context = LocalContext.current
                    )
                }
                composable("register") {
                    RegisterScreen(
                        onRegister = { email, password, confirmPassword, ctx ->
                            when {
                                email.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                                    android.widget.Toast.makeText(
                                        ctx,
                                        "Заполните все поля",
                                        android.widget.Toast.LENGTH_SHORT
                                    ).show()
                                }

                                password != confirmPassword -> {
                                    android.widget.Toast.makeText(
                                        ctx,
                                        "Пароли не совпадают",
                                        android.widget.Toast.LENGTH_SHORT
                                    ).show()
                                }

                                !registerUser(ctx, email, password) -> {
                                    android.widget.Toast.makeText(
                                        ctx,
                                        "Данное имя уже занято",
                                        android.widget.Toast.LENGTH_SHORT
                                    ).show()
                                }

                                else -> {
                                    saveUserSession(ctx, true)
                                    isLoggedIn = true
                                    navController.navigate("home") {
                                        popUpTo("register") { inclusive = true }
                                    }
                                }
                            }
                        },
                        context = LocalContext.current
                    )
                }
                composable("home") {
                    HomeScreen(
                        onLogout = {
                            saveUserSession(context, false)
                            isLoggedIn = false
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }
                        },
                        onAddSneaker = { navController.navigate("addSneaker") }
                    )
                }
                composable("addSneaker") {
                    AddSneakerScreen(
                        onSneakerAdded = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}