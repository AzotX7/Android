package com.azot.homework6.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.azot.homework6.data.Sneaker
import com.azot.homework6.viewmodel.SneakerViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AddSneakerScreen(
    onSneakerAdded: () -> Unit,
    sneakerViewModel: SneakerViewModel = viewModel()
) {
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var yearText by remember { mutableStateOf("") }
    var ratingText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Добавить кроссовок", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Название") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = brand,
            onValueChange = { brand = it },
            label = { Text("Бренд") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Описание") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = yearText,
            onValueChange = { yearText = it },
            label = { Text("Год выпуска") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = ratingText,
            onValueChange = { ratingText = it },
            label = { Text("Оценка критиков") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val year = yearText.toIntOrNull()
                val rating = ratingText.toFloatOrNull()
                if (title.isBlank() || brand.isBlank() || description.isBlank() || year == null || rating == null) {
                    Toast.makeText(context, "Заполните все поля корректно", Toast.LENGTH_SHORT).show()
                } else {
                    val sneaker = Sneaker(
                        title = title,
                        brand = brand,
                        description = description,
                        year = year,
                        rating = rating
                    )
                    sneakerViewModel.insert(sneaker)
                    onSneakerAdded()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Добавить")
        }
    }
}