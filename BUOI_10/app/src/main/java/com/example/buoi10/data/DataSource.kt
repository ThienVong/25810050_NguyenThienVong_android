package com.example.buoi10.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

interface DataSource {
    fun counts(): Flow<Int>
}

class FakeDataSource : DataSource {
    private val flow = MutableSharedFlow<Int>()

    suspend fun emit(value: Int) = flow.emit(value)

    override fun counts(): Flow<Int> = flow
}

class StaticFakeRepository {
    fun observeCount(): Flow<Int> = flow {
        emit(10)
    }

    fun observeChatMessages(): Flow<List<String>> = flow {
        emit(listOf("Hello", "World", "Flow Testing"))
    }
}
