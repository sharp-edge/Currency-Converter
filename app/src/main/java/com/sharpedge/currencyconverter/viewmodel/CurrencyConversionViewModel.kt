package com.sharpedge.currencyconverter.viewmodel

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharpedge.currencyconverter.data.database.CurrencyHistory
import com.sharpedge.currencyconverter.ui.state.CurrencyViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.sharpedge.currencyconverter.ui.state.ErrorType
import com.sharpedge.currencyconverter.usecase.api.IGetConversionRatesUseCase
import com.sharpedge.currencyconverter.usecase.api.IGetCurrencySymbolsUseCase
import com.sharpedge.currencyconverter.usecase.database.ISaveRecordUseCase
import java.util.Date
import com.sharpedge.currencyconverter.utils.Result
import java.math.BigDecimal
import java.math.RoundingMode


@HiltViewModel
class CurrencyConversionViewModel @Inject constructor(
    private val getCurrencySymbolsUseCase: IGetCurrencySymbolsUseCase,
    private val getConversionRatesUseCase: IGetConversionRatesUseCase,
    private val iSaveRecordUseCase: ISaveRecordUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(CurrencyViewState())
    val viewState: StateFlow<CurrencyViewState> = _viewState.asStateFlow()


    init {
        fetchCurrencySymbols()

    }

    private fun fetchCurrencySymbols() = viewModelScope.launch {
        Log.d("Sarmad", "fetchCurrencySymbols() is called")
        _viewState.value = _viewState.value.copy(isLoading = true)
        val result = getCurrencySymbolsUseCase.fetchCurrencySymbols()
        _viewState.value = when (result) {
            is Result.Success -> _viewState.value.copy(
                isLoading = false,
                currencySymbols = result.data.map { it.code }
            )

            is Result.Failure -> _viewState.value.copy(
                fromCurrency = null,
                toCurrency = null,
                isLoading = false,
                error = result.errorType
            )
        }
    }

    fun fetchConversionRates(base: String) = viewModelScope.launch {
        _viewState.value = _viewState.value.copy(isLoading = true)
        val result = getConversionRatesUseCase.fetchBaseCurrencies(base)
        _viewState.value = when (result) {
            is Result.Success -> {
                _viewState.value.copy(
                    isLoading = false,
                    conversionRates = result.data.rates,
                    fromCurrency = base
                )

            }

            is Result.Failure -> _viewState.value.copy(
                isLoading = false,
                fromCurrency = null,
                toCurrency = null,
                error = result.errorType
            )
        }

    }

    fun convertCurrency(amount: Double, toCurrency: String) {
        val rate = _viewState.value.conversionRates?.get(toCurrency)
        if (rate != null) {
            val result = BigDecimal(amount * rate).setScale(4, RoundingMode.HALF_UP).stripTrailingZeros()
            _viewState.value =
                _viewState.value.copy(conversionResult = result.toPlainString(), error = null)
        } else {
            _viewState.value =
                _viewState.value.copy(error = ErrorType.Custom("Conversion rate not available."))
        }
    }


    fun swapCurrencies() = viewModelScope.launch {
        val currentState = _viewState.value
        val toCurrency = currentState.toCurrency
        val fromCurrency = currentState.fromCurrency
        Log.d(
            "Sarmad",
            "After swapCurrencies = currentState.fromCurrency = $fromCurrency"
        )
        Log.d("Sarmad", "After swapCurrencies = currentState.toCurrency = $toCurrency")

        if (toCurrency != null && fromCurrency != null) {

            setFromCurrency(toCurrency)
            setToCurrency(fromCurrency)
            Log.d(
                "Sarmad",
                "After swapCurrencies = currentState.fromCurrency = ${currentState.fromCurrency}"
            )
            Log.d(
                "Sarmad",
                "After swapCurrencies = currentState.toCurrency = ${currentState.toCurrency}"
            )

        } else {
            _viewState.value =
                _viewState.value.copy(error = ErrorType.Custom("TO / FROM currencies cannot be empty"))
        }
    }

    fun setFromCurrency(fromCurrency: String) {
        _viewState.value = _viewState.value.copy(fromCurrency = fromCurrency)
        fetchConversionRates(fromCurrency)
        saveRecord()
    }

    fun setToCurrency(toCurrency: String) {
        _viewState.value = _viewState.value.copy(toCurrency = toCurrency)
        convertCurrency(_viewState.value.conversionAmount.toDouble(), toCurrency)
        saveRecord()
    }

    fun setConversionAmount(amount: String) {
        if (amount.isEmpty() || !amount.isDigitsOnly()) {
            _viewState.value = _viewState.value.copy(error = ErrorType.Custom("Invalid amount"))
        } else {
            _viewState.value = _viewState.value.copy(conversionAmount = amount.toInt())
            val toCurrency = _viewState.value.toCurrency
            val fromCurrency = _viewState.value.fromCurrency
            // this means that user has already selected FROM and TO currencies and when user changes the amount convert it on the fly
            if (fromCurrency != null && toCurrency != null && amount.toDouble() > 0) {

                convertCurrency(amount.toDouble(), toCurrency)

            }
        }

    }


    fun clearError() {
        _viewState.value = _viewState.value.copy(error = null)
    }


    private fun saveRecord() = viewModelScope.launch {

        val fromCurrency = _viewState.value.fromCurrency
        val toCurrency = _viewState.value.toCurrency
        if (fromCurrency != null && toCurrency != null) {
            val record = CurrencyHistory(
                fromCurrency = fromCurrency,
                toCurrency = toCurrency,
                timestamp = Date().time
            )
            when (val result = iSaveRecordUseCase.execute(record)) {
                is Result.Success -> {
                    // Do nothing for the success case since we want to silently perform DB insert
                }

                is Result.Failure -> {
                    _viewState.value = _viewState.value.copy(error = result.errorType)
                }

            }
        }

    }

}