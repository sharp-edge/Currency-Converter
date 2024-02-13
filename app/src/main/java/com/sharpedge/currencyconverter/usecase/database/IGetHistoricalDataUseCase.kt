package com.sharpedge.currencyconverter.usecase.database


import com.sharpedge.currencyconverter.ui.model.CurrencyRecordUIModel
import com.sharpedge.currencyconverter.utils.Result

interface IGetHistoricalDataUseCase {


    suspend fun execute(since: Long): Result<List<CurrencyRecordUIModel>>

}