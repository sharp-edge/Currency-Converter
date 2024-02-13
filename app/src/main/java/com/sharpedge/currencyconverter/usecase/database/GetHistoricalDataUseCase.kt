package com.sharpedge.currencyconverter.usecase.database

import com.sharpedge.currencyconverter.mapper.toUIModel
import com.sharpedge.currencyconverter.repository.CurrencyRepository
import com.sharpedge.currencyconverter.ui.model.CurrencyRecordUIModel
import com.sharpedge.currencyconverter.utils.Result
import javax.inject.Inject

class GetHistoricalDataUseCase @Inject constructor(
    private val repository: CurrencyRepository
) : IGetHistoricalDataUseCase {
    override suspend fun execute(since: Long): Result<List<CurrencyRecordUIModel>> {
        val result = repository.getHistoricalData(since)

        return when (result) {
            is Result.Success -> {

                val uiModels = result.data.map { it.toUIModel() }

                Result.Success(uiModels)
            }

            is Result.Failure -> {

                Result.Failure(result.errorType)
            }
        }

    }
}
