package com.example.inventory.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.inventory.data.Item
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryApp(
    homeViewModel: HomeViewModel,
    itemEntryViewModel: ItemEntryViewModel,
    modifier: Modifier = Modifier
) {
    val homeUiState by homeViewModel.homeUiState.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inventory (Room Database)") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Item"
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        HomeScreen(
            itemList = homeUiState.itemList,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )

        if (showAddDialog) {
            AddItemDialog(
                itemUiState = itemEntryViewModel.itemUiState,
                onItemValueChange = itemEntryViewModel::updateUiState,
                onSaveClick = {
                    coroutineScope.launch {
                        itemEntryViewModel.saveItem()
                        showAddDialog = false
                    }
                },
                onDismiss = { showAddDialog = false }
            )
        }
    }
}

@Composable
fun HomeScreen(
    itemList: List<Item>,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp)
    ) {
        if (itemList.isEmpty()) {
            Text(
                text = "No items in inventory. Click + to add!",
                fontSize = 18.sp,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            InventoryList(itemList = itemList)
        }
    }
}

@Composable
fun InventoryList(
    itemList: List<Item>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(items = itemList, key = { it.id }) { item ->
            InventoryItem(item = item)
        }
    }
}

@Composable
fun InventoryItem(
    item: Item,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$${item.price}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = "Quantity: ${item.quantity}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun AddItemDialog(
    itemUiState: ItemUiState,
    onItemValueChange: (ItemDetails) -> Unit,
    onSaveClick: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Add Item to Room",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = itemUiState.itemDetails.name,
                    onValueChange = { onItemValueChange(itemUiState.itemDetails.copy(name = it)) },
                    label = { Text("Item Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = itemUiState.itemDetails.price,
                    onValueChange = { onItemValueChange(itemUiState.itemDetails.copy(price = it)) },
                    label = { Text("Price ($)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = itemUiState.itemDetails.quantity,
                    onValueChange = { onItemValueChange(itemUiState.itemDetails.copy(quantity = it)) },
                    label = { Text("Quantity") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onSaveClick,
                        enabled = itemUiState.isEntryValid
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}
