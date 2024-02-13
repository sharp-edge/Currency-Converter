package com.sharpedge.currencyconverter.usecase.api

import com.sharpedge.currencyconverter.mapper.mapToUIModel
import com.sharpedge.currencyconverter.network.api.error.ApiException
import com.sharpedge.currencyconverter.repository.CurrencyRepository

import com.sharpedge.currencyconverter.ui.model.CurrencySymbolUIModel
import com.sharpedge.currencyconverter.ui.state.ErrorType

import okio.IOException
import javax.inject.Inject
import com.sharpedge.currencyconverter.utils.*

class GetCurrencySymbolsUseCase @Inject constructor(
    private val repository: CurrencyRepository
) : IGetCurrencySymbolsUseCase {
    override suspend fun fetchCurrencySymbols(): Result<List<CurrencySymbolUIModel>> {
        return try {
            val response = repository.getAvailableSymbols()
            Result.Success(response.mapToUIModel())
        } catch (exception: IOException) {
            Result.Failure(ErrorType.NetworkError)
        } catch (exception: ApiException) {

            Result.Failure(exception.errorType)

        } catch (exception: Exception) {

            Result.Failure(ErrorType.UnknownError)
        }
    }
}
