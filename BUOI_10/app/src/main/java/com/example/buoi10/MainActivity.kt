package com.example.buoi10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.inventory.InventoryApplication
import com.example.inventory.ui.HomeViewModel
import com.example.inventory.ui.InventoryApp
import com.example.inventory.ui.ItemEntryViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appContainer = (application as InventoryApplication).container
        val homeViewModel = HomeViewModel(appContainer.itemsRepository)
        val itemEntryViewModel = ItemEntryViewModel(appContainer.itemsRepository)

        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    InventoryApp(
                        homeViewModel = homeViewModel,
                        itemEntryViewModel = itemEntryViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
