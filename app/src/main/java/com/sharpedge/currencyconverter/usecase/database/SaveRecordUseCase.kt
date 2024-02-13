package com.sharpedge.currencyconverter.usecase.database


import com.sharpedge.currencyconverter.data.database.CurrencyHistory
import com.sharpedge.currencyconverter.repository.CurrencyRepository
import com.sharpedge.currencyconverter.utils.Result
import javax.inject.Inject

class SaveRecordUseCase @Inject constructor(
    private val repository: CurrencyRepository
): ISaveRecordUseCase {
    override suspend fun execute(record: CurrencyHistory): Result<Unit> {
        return repository.saveConversionRecord(record)
    }

}