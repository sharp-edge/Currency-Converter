package com.sharpedge.currencyconverter.usecase.database
import com.sharpedge.currencyconverter.data.database.CurrencyHistory
import com.sharpedge.currencyconverter.utils.Result

interface ISaveRecordUseCase {

    suspend fun execute(record: CurrencyHistory): Result<Unit>

}