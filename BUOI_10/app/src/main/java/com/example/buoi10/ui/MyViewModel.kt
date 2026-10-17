package com.example.buoi10.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buoi10.data.Repository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MyViewModelWithStateIn(repository: Repository) : ViewModel() {
    val score: StateFlow<Int> = repository.scores()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = 0
        )
}
