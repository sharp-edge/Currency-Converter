package com.sharpedge.currencyconverter.network



import com.sharpedge.currencyconverter.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RequestInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()

        val originalHttpUrl = originalRequest.url


        val urlWithApiKey = originalHttpUrl.newBuilder()
            .addQueryParameter("access_key", Constants.API_KEY)
            .build()


        val newRequest = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()


        return chain.proceed(newRequest)
    }
}