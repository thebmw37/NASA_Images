package com.nasaImages

import com.nasaImages.model.MainViewModel
import com.nasaImages.repository.MockNasaImagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class NasaImagesUnitTests {

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: MainViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(MockNasaImagesRepository(), StandardTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun performSearchWithValidPageNumber() {
        runTest {
            viewModel.performSearch(1)
        }
        assertEquals(1, viewModel.currentPage.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun performSearchWithLowPageNumber() {
        runTest {
            viewModel.performSearch(1)
            viewModel.performSearch(0)
        }
        assertEquals(1, viewModel.currentPage.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun performSearchWithHighPageNumber() {
        runTest {
            viewModel.performSearch(100)
            viewModel.performSearch(101)
        }
        assertEquals(100, viewModel.currentPage.value)
    }

}