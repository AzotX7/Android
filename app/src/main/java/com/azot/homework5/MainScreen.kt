package com.azot.homework5

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*

@Composable
fun MainScreen(activity: Activity, permissionLauncher: ActivityResultLauncher<String>) {
    var coroutineCount by remember { mutableStateOf("") }
    var executionMode by remember { mutableStateOf("Sequential") }
    var cancellationMode by remember { mutableStateOf("Cancel on Minimize") }
    var dispatcher by remember { mutableStateOf("Default") }
    var runningCoroutines by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        // Кнопка запроса разрешений на уведомления
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Button(onClick = {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }) {
                Text("Request Notification Permission")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Поле ввода количества корутин
        OutlinedTextField(
            value = coroutineCount,
            onValueChange = { coroutineCount = it },
            label = { Text("Number of Coroutines") },
            isError = coroutineCount.isBlank(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Выбор способа запуска корутин
        Text("Execution Mode:")
        Row {
            listOf("Sequential", "Parallel").forEach {
                Row(Modifier.padding(end = 8.dp)) {
                    RadioButton(selected = executionMode == it, onClick = { executionMode = it })
                    Text(it, Modifier.padding(start = 4.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Выбор режима отмены корутин
        Text("Cancellation Mode:")
        Row {
            listOf("Cancel on Minimize", "Continue on Minimize").forEach {
                Row(Modifier.padding(end = 8.dp)) {
                    RadioButton(selected = cancellationMode == it, onClick = { cancellationMode = it })
                    Text(it, Modifier.padding(start = 4.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Выбор диспетчера корутин (Default, IO, Main)
        Text("Dispatcher:")
        DropdownMenuExample(dispatcher) { dispatcher = it }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка запуска корутин
        Button(
            onClick = {
                if (coroutineCount.isBlank()) return@Button
                isRunning = true
                runningCoroutines = coroutineCount.toInt()
                startCoroutines(
                    count = coroutineCount.toInt(),
                    mode = executionMode,
                    dispatcher = dispatcher,
                    onComplete = { isRunning = false }
                )
            },
            enabled = !isRunning
        ) {
            Text("Start")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Кнопка отмены выполнения корутин
        Button(
            onClick = {
                coroutineScope.cancel()
                isRunning = false
            },
            enabled = isRunning
        ) {
            Text("Cancel ($runningCoroutines canceled)")
        }

        // Показываем сообщение, если отменить нечего
        if (!isRunning && runningCoroutines == 0) {
            Text("No coroutines running", color = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun DropdownMenuExample(selected: String, onSelection: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Default", "IO", "Main")

    Box {
        Button(onClick = { expanded = true }) {
            Text(selected)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach {
                DropdownMenuItem(text = { Text(it) }, onClick = {
                    onSelection(it)
                    expanded = false
                })
            }
        }
    }
}

// Запуск корутин с выбранными параметрами
fun startCoroutines(count: Int, mode: String, dispatcher: String, onComplete: () -> Unit) {
    val scope = CoroutineScope(
        when (dispatcher) {
            "IO" -> Dispatchers.IO
            "Main" -> Dispatchers.Main
            else -> Dispatchers.Default
        }
    )

    if (mode == "Sequential") {
        scope.launch {
            repeat(count) {
                delay(1000)
            }
            onComplete()
        }
    } else {
        repeat(count) {
            scope.launch {
                delay(1000)
                onComplete()
            }
        }
    }
}