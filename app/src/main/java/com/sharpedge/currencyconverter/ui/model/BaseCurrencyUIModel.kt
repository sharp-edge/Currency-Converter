package com.sharpedge.currencyconverter.ui.model

data class BaseCurrencyUIModel(

    // Keeping rates as a Map for direct access.
    val rates: LinkedHashMap<String, Double>,

)