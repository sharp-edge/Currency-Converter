package com.sharpedge.currencyconverter.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sharpedge.currencyconverter.ui.model.BaseCurrencyUIModel
import com.sharpedge.currencyconverter.ui.model.CurrencySymbolUIModel
import com.sharpedge.currencyconverter.ui.state.ErrorType
import com.sharpedge.currencyconverter.usecase.api.IGetConversionRatesUseCase
import com.sharpedge.currencyconverter.usecase.api.IGetCurrencySymbolsUseCase
import com.sharpedge.currencyconverter.usecase.database.ISaveRecordUseCase
import com.sharpedge.currencyconverter.viewmodel.CurrencyConversionViewModel
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import com.sharpedge.currencyconverter.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class CurrencyConversionViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getCurrencySymbolsUseCase: IGetCurrencySymbolsUseCase

    @Mock
    private lateinit var getConversionRatesUseCase: IGetConversionRatesUseCase

    @Mock
    private lateinit var saveRecordUseCase: ISaveRecordUseCase

    private lateinit var viewModel: CurrencyConversionViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        MockitoAnnotations.openMocks(this)
        viewModel = CurrencyConversionViewModel(
            getCurrencySymbolsUseCase,
            getConversionRatesUseCase,
            saveRecordUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `fetchCurrencySymbols is called on initialization`() = runTest {

        verify(getCurrencySymbolsUseCase).fetchCurrencySymbols()
    }


    @Test
    fun `fetchCurrencySymbols success updates viewState correctly`() = runTest {

        val symbols = listOf(CurrencySymbolUIModel("EUR", "Euro"))
        `when`(getCurrencySymbolsUseCase.fetchCurrencySymbols()).thenReturn(Result.Success(symbols))

        viewModel.fetchCurrencySymbols()

        assert(viewModel.viewState.value.currencySymbols.isNotEmpty())
    }

    @Test
    fun `fetchCurrencySymbols failure updates viewState with error`() = runTest {

        `when`(getCurrencySymbolsUseCase.fetchCurrencySymbols()).thenReturn(
            Result.Failure(
                ErrorType.Custom(
                    "Test error"
                )
            )
        )

        viewModel.fetchCurrencySymbols()

        assertNotNull(viewModel.viewState.value.error)
    }


    @Test
    fun `convertCurrency with valid rate updates conversion result`() = runTest {

        val symbolsResponse = listOf(CurrencySymbolUIModel("USD", "United States Dollar"))
        whenever(getCurrencySymbolsUseCase.fetchCurrencySymbols()).thenReturn(Result.Success(symbolsResponse))

        val baseCurrency = "EUR"
        val targetCurrency = "USD"
        val conversionRate = 0.85
        whenever(getConversionRatesUseCase.fetchBaseCurrencies(baseCurrency)).thenReturn(
            Result.Success(BaseCurrencyUIModel(rates = linkedMapOf(targetCurrency to conversionRate)))
        )


        viewModel = CurrencyConversionViewModel(getCurrencySymbolsUseCase, getConversionRatesUseCase, saveRecordUseCase)


        viewModel.fetchConversionRates(baseCurrency)
        advanceUntilIdle()

        viewModel.convertCurrency(100.0, targetCurrency)
        advanceUntilIdle()


        val actualConversionResult = viewModel.viewState.value.conversionResult
        assertTrue("Expected result to start with '85', got '$actualConversionResult'", actualConversionResult!!.startsWith("85"), )
    }





    @Test
    fun `convertCurrency with invalid rate updates error`() = runTest {

        val baseCurrency = "EUR"
        whenever(getConversionRatesUseCase.fetchBaseCurrencies(baseCurrency))
            .thenReturn(Result.Failure(ErrorType.Custom("Error fetching conversion rates")))


        viewModel.fetchConversionRates(baseCurrency)


        viewModel.convertCurrency(100.0, "USD")
        assertTrue("Expected a custom error due to invalid conversion rates.", viewModel.viewState.value.error is ErrorType.Custom)
    }

    @Test
    fun `clearError clears the error state`() = runTest {
        viewModel.clearError()
        assertEquals(null, viewModel.viewState.value.error)
    }


    @Test
    fun `swapCurrencies with null from or to currency updates viewState with error`() = runTest {
        viewModel.swapCurrencies()
        assertTrue(viewModel.viewState.value.error is ErrorType.Custom)
    }


    @Test
    fun `setConversionAmount with invalid input updates viewState with error`() = runTest {
        viewModel.setConversionAmount("invalid")
        assertNotNull(viewModel.viewState.value.error)
    }





}
