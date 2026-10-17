package com.example.buoi10.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository(private val dataSource: DataSource) {
    fun scores(): Flow<Int> {
        return dataSource.counts().map { it * 10 }
    }
}
