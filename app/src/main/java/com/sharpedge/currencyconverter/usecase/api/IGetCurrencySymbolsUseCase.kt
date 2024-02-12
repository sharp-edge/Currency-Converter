package com.sharpedge.currencyconverter.usecase.api

import com.sharpedge.currencyconverter.ui.model.CurrencySymbolUIModel
import com.sharpedge.currencyconverter.utils.Result

interface IGetCurrencySymbolsUseCase {

    suspend fun fetchCurrencySymbols(): Result<List<CurrencySymbolUIModel>>
}