package com.sharpedge.currencyconverter.repository


import com.sharpedge.currencyconverter.data.database.CurrencyHistory
import com.sharpedge.currencyconverter.model.BaseCurrencyApiResponseModel
import com.sharpedge.currencyconverter.model.SymbolsApiResponseModel
import com.sharpedge.currencyconverter.utils.Result

interface ICurrencyRepository {

    suspend fun getAvailableSymbols(): SymbolsApiResponseModel

    suspend fun getBaseCurrency(base: String): BaseCurrencyApiResponseModel


    suspend fun saveConversionRecord(record: CurrencyHistory): Result<Unit>

    suspend fun getHistoricalData(since: Long): Result<List<CurrencyHistory>>




}