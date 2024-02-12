package com.sharpedge.currencyconverter.repository


import com.sharpedge.currencyconverter.model.BaseCurrencyApiResponseModel
import com.sharpedge.currencyconverter.model.SymbolsApiResponseModel

interface ICurrencyRepository {

    suspend fun getAvailableSymbols(): SymbolsApiResponseModel

    suspend fun getBaseCurrency(base: String): BaseCurrencyApiResponseModel

    // TODO have to add more functions




}