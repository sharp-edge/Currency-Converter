package com.sharpedge.currencyconverter.usecase.api

import com.sharpedge.currencyconverter.ui.model.BaseCurrencyUIModel
import com.sharpedge.currencyconverter.utils.Result

interface IGetConversionRatesUseCase {

    suspend fun fetchBaseCurrencies(base: String): Result<BaseCurrencyUIModel>

}