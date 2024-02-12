package com.sharpedge.currencyconverter.ui.state

data class CurrencyViewState(
    val isLoading: Boolean = false,
    val error: ErrorType? = null,
    val currencySymbols: List<String> = emptyList(),
    val conversionRates: Map<String, Double>? = null,
    val fromCurrency: String? = null,
    val toCurrency: String? = null,
    val conversionAmount: Int = 1,
    val conversionResult: String? = null
)
