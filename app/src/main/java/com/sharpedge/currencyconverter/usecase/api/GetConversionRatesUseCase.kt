package com.sharpedge.currencyconverter.usecase.api

import com.sharpedge.currencyconverter.mapper.toUIModel
import com.sharpedge.currencyconverter.network.api.error.ApiException
import com.sharpedge.currencyconverter.repository.CurrencyRepository
import com.sharpedge.currencyconverter.ui.model.BaseCurrencyUIModel
import com.sharpedge.currencyconverter.ui.state.ErrorType
import com.sharpedge.currencyconverter.utils.Result
import java.io.IOException
import javax.inject.Inject


class GetConversionRatesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) : IGetConversionRatesUseCase {

    override suspend fun fetchBaseCurrencies(base: String): Result<BaseCurrencyUIModel> {
        return try {
            val response = repository.getBaseCurrency(base)
            Result.Success(response.toUIModel())
        } catch (exception: IOException) {
            Result.Failure(ErrorType.NetworkError)
        } catch (exception: ApiException) {
            Result.Failure(exception.errorType)
        } catch (exception: Exception) {
            Result.Failure(ErrorType.UnknownError)
        }
    }
}