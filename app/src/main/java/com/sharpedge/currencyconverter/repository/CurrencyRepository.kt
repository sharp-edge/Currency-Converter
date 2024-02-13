package com.sharpedge.currencyconverter.repository

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.sharpedge.currencyconverter.data.database.CurrencyHistory
import com.sharpedge.currencyconverter.data.database.CurrencyHistoryDao
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import com.sharpedge.currencyconverter.model.BaseCurrencyApiResponseModel
import com.sharpedge.currencyconverter.model.SymbolsApiResponseModel
import com.sharpedge.currencyconverter.network.api.CurrencyService
import com.sharpedge.currencyconverter.network.api.error.ApiException
import com.sharpedge.currencyconverter.network.api.error.ErrorDetail
import com.sharpedge.currencyconverter.ui.state.ErrorType
import com.sharpedge.currencyconverter.utils.Result

class CurrencyRepository @Inject constructor(
    private val currencyService: CurrencyService,
    private val gson: Gson,
    private val currencyHistoryDao: CurrencyHistoryDao

) : ICurrencyRepository {

    override suspend fun getAvailableSymbols(): SymbolsApiResponseModel {
        val response = currencyService.getAvailableSymbols()
        return processResponse(response)

    }

    override suspend fun getBaseCurrency(base: String): BaseCurrencyApiResponseModel {
        val response = currencyService.getBaseCurrency(base)
        return processResponse(response)
    }


    private inline fun <reified T> processResponse(response: Response<ResponseBody>): T {
        if (response.isSuccessful) {
            val responseBody = response.body()?.string()
            val jsonElement = JsonParser().parse(responseBody)

            if (jsonElement.isJsonObject) {
                val jsonObject = jsonElement.asJsonObject
                if (jsonObject.has("success") && !jsonObject["success"].asBoolean) {

                    val error = gson.fromJson(jsonObject["error"], ErrorDetail::class.java)
                    val errorMessagePart = error.info ?: error.type
                    throw ApiException(ErrorType.Custom("Error ${error.code}: $errorMessagePart"))
                }
            }


            return gson.fromJson(responseBody, T::class.java)
        } else {

            when (response.code()) {
                in 400..499 -> throw ApiException(ErrorType.NotFoundError)
                in 500..599 -> throw ApiException(ErrorType.ServerError)
                else -> throw ApiException(ErrorType.UnknownError)
            }
        }
    }



    override suspend fun saveConversionRecord(record: CurrencyHistory): Result<Unit> {
        return try {
            currencyHistoryDao.insertRecord(record)
            Result.Success(Unit)
        } catch (ex: Exception) {
            Result.Failure(ErrorType.DatabaseError("Failed to save conversion record: ${ex.message}"))
        }
    }

    override suspend fun getHistoricalData(since: Long): Result<List<CurrencyHistory>> {
        return try {
            val data = currencyHistoryDao.getHistoricalData(since)
            Result.Success(data)
        } catch (ex: Exception) {
            Result.Failure(ErrorType.DatabaseError("Failed to fetch historical data: ${ex.message}"))
        }
    }


}