package com.nasaImages

import com.nasaImages.model.MainViewModel
import com.nasaImages.repository.MockNasaImagesRepository
import com.nasaImages.repository.NasaImagesRepository
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
    private lateinit var repository: NasaImagesRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = MockNasaImagesRepository()
        viewModel = MainViewModel(repository, StandardTestDispatcher())
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testSearchUiWithZeroSizeResult() {
        runTest {
            (repository as MockNasaImagesRepository).setEmptySearchResult()
            viewModel.performSearch(1)
            assertTrue(viewModel.progressIndicatorVisible.value)
            assertFalse(viewModel.infoTextVisible.value)
            assertFalse(viewModel.navigationVisible.value)
        }

        assertFalse(viewModel.progressIndicatorVisible.value)
        assertTrue(viewModel.infoTextVisible.value)
        assertFalse(viewModel.navigationVisible.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testSearchUiWithNormalResult() {
        runTest {
            (repository as MockNasaImagesRepository).setNormalSearchResult()
            viewModel.performSearch(1)
            assertTrue(viewModel.progressIndicatorVisible.value)
            assertFalse(viewModel.infoTextVisible.value)
            assertFalse(viewModel.navigationVisible.value)
        }

        assertFalse(viewModel.progressIndicatorVisible.value)
        assertFalse(viewModel.infoTextVisible.value)
        assertTrue(viewModel.navigationVisible.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testSearchUiWithErrorResult() {
        runTest {
            (repository as MockNasaImagesRepository).setErrorResult()
            viewModel.performSearch(1)
        }

        assertTrue(viewModel.errorModalVisible.value)
        assertFalse(viewModel.progressIndicatorVisible.value)
        assertFalse(viewModel.infoTextVisible.value)
        assertFalse(viewModel.navigationVisible.value)
    }

}