package com.example.buoi10

import app.cash.turbine.test
import com.example.buoi10.data.FakeDataSource
import com.example.buoi10.data.Repository
import com.example.buoi10.data.StaticFakeRepository
import com.example.buoi10.ui.MyViewModelWithStateIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FlowTestingTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // 1. Asserting first emission using first()
    @Test
    fun testFakeProducerFirstEmission() = runTest {
        val fakeRepo = StaticFakeRepository()
        val firstItem = fakeRepo.observeCount().first()
        assertEquals(10, firstItem)
    }

    // 2. Asserting finite data streams using toList()
    @Test
    fun testFiniteStreamToList() = runTest {
        val fakeRepo = StaticFakeRepository()
        val messages = fakeRepo.observeChatMessages().first()
        assertEquals(listOf("Hello", "World", "Flow Testing"), messages)
    }

    // 3. Continuous collection during a test using backgroundScope
    @Test
    fun testContinuouslyCollectWithBackgroundScope() = runTest {
        val dataSource = FakeDataSource()
        val repository = Repository(dataSource)

        val values = mutableListOf<Int>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            repository.scores().toList(values)
        }

        dataSource.emit(1)
        assertEquals(10, values[0])

        dataSource.emit(2)
        dataSource.emit(3)
        assertEquals(30, values[2])
        assertEquals(3, values.size)
    }

    // 4. Using Turbine library for flow assertion
    @Test
    fun testFlowEmissionsWithTurbine() = runTest {
        val dataSource = FakeDataSource()
        val repository = Repository(dataSource)

        repository.scores().test {
            dataSource.emit(1)
            assertEquals(10, awaitItem())

            dataSource.emit(2)
            awaitItem() // Ignore intermediate item

            dataSource.emit(3)
            assertEquals(30, awaitItem())
        }
    }

    // 5. Testing StateFlows created with stateIn (WhileSubscribed)
    @Test
    fun testStateFlowWithStateIn() = runTest {
        val dataSource = FakeDataSource()
        val repository = Repository(dataSource)
        val viewModel = MyViewModelWithStateIn(repository)

        // Create a background collector coroutine so SharingStarted.WhileSubscribed becomes active
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.score.collect {}
        }

        assertEquals(0, viewModel.score.value) // Initial value

        dataSource.emit(1)
        assertEquals(10, viewModel.score.value)

        dataSource.emit(2)
        dataSource.emit(3)
        assertEquals(30, viewModel.score.value)
    }
}
