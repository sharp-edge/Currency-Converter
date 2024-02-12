package com.sharpedge.currencyconverter.ui.model

data class BaseCurrencyUIModel(
    val baseCurrency: String,
    val date: String,
    // Keeping rates as a Map for direct access.
    val rates: LinkedHashMap<String, Double>,
    val isSuccess: Boolean,
    val message: String?
)