package com.azot.homework6.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.azot.homework6.data.Sneaker
import com.azot.homework6.viewmodel.SneakerViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    onAddSneaker: () -> Unit,
    sneakerViewModel: SneakerViewModel = viewModel()
) {
    val sneakerList by sneakerViewModel.allSneakers.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Главный экран") },
                actions = {
                    TextButton(onClick = onLogout) {
                        Text("Выйти")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddSneaker) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить")
            }
        }
    ) { paddingValues ->
        if (sneakerList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("Нет добавленных кроссовок")
            }
        } else {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.fillMaxSize()
            ) {
                items(sneakerList) { sneaker ->
                    SneakerItem(sneaker = sneaker, onDelete = { sneakerViewModel.delete(sneaker) })
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SneakerItem(sneaker: Sneaker, onDelete: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Удалить кроссовок") },
            text = { Text("Вы действительно хотите удалить \"${sneaker.title}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete()
                    showDialog = false
                }) {
                    Text("Удалить")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(
                onClick = {  },
                onLongClick = { showDialog = true }
            ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = sneaker.title, style = MaterialTheme.typography.titleMedium)
                Text(text = "Бренд: ${sneaker.brand}")
                Text(text = "Год: ${sneaker.year}")
                Text(text = "Оценка: ${sneaker.rating}")
            }
        }
    }
}