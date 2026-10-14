package com.example.dessertclicker

import com.example.dessertclicker.ui.DessertViewModel
import org.junit.Assert.*
import org.junit.Test

class DessertViewModelTest {
    @Test fun startsEmptyAndChargesOldPriceAtThreshold() {
        val model = DessertViewModel()
        assertEquals(0,model.uiState.value.revenue)
        assertEquals(0,model.uiState.value.dessertsSold)
        repeat(5) { model.sellDessert() }
        assertEquals(25,model.uiState.value.revenue)
        assertEquals(10,model.uiState.value.currentDessert.price)
        model.sellDessert()
        assertEquals(35,model.uiState.value.revenue)
        assertEquals(6,model.uiState.value.dessertsSold)
    }
    @Test fun advancesToEclairAtTwentySales() {
        val model = DessertViewModel()
        repeat(20) { model.sellDessert() }
        assertEquals(175,model.uiState.value.revenue)
        assertEquals(15,model.uiState.value.currentDessert.price)
        model.sellDessert()
        assertEquals(190,model.uiState.value.revenue)
    }
}
