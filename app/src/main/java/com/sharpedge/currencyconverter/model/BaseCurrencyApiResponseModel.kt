package com.sharpedge.currencyconverter.model

data class BaseCurrencyApiResponseModel(
    val base: String,
    val date: String,
    val rates: LinkedHashMap<String, Double>,
    val success: Boolean,
    val message: String
)
