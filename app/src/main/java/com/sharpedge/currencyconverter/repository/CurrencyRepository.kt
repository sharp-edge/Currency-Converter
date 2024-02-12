package com.sharpedge.currencyconverter.repository

import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import com.sharpedge.currencyconverter.model.BaseCurrencyApiResponseModel
import com.sharpedge.currencyconverter.model.SymbolsApiResponseModel
import com.sharpedge.currencyconverter.network.api.CurrencyService
import com.sharpedge.currencyconverter.network.api.error.ApiException
import com.sharpedge.currencyconverter.network.api.error.ErrorDetail
import com.sharpedge.currencyconverter.ui.state.ErrorType

class CurrencyRepository @Inject constructor(
    private val currencyService: CurrencyService,
    private val gson: Gson,
    // TODO will need to inject more dependencies

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


    // TODO Need to add more repo functions for DB related operations


}