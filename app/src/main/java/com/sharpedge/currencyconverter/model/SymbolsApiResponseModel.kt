package com.sharpedge.currencyconverter.model

data class SymbolsApiResponseModel(
    val success: Boolean,
    val symbols: Map<String, String>
)