package com.sharpedge.currencyconverter.network.api


import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {

    @GET("symbols")
    suspend fun getAvailableSymbols() : Response<ResponseBody>

    @GET("latest")
    suspend fun getBaseCurrency(@Query("base") base: String) : Response<ResponseBody>
}