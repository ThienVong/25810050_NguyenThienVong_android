package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource
import com.example.dessertclicker.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class DessertUiState(
    val revenue: Int = 0,
    val dessertsSold: Int = 0,
    val currentDessert: Dessert = Datasource.dessertList.first()
)

class DessertViewModel : ViewModel() {
    private val desserts = Datasource.dessertList
    private val mutableState = MutableStateFlow(DessertUiState())
    val uiState = mutableState.asStateFlow()

    fun sellDessert() {
        mutableState.update { old ->
            val count = old.dessertsSold + 1
            old.copy(revenue = old.revenue + old.currentDessert.price,
                dessertsSold = count,
                currentDessert = desserts.last { count >= it.startProductionAmount })
        }
    }
}
