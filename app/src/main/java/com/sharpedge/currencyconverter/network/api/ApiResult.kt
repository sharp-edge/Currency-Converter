package com.sharpedge.currencyconverter.network.api

import com.sharpedge.currencyconverter.ui.state.ErrorType

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val errorType: ErrorType, val message: String? = null) : ApiResult<Nothing>()
}
