package com.sharpedge.currencyconverter.utils

import com.sharpedge.currencyconverter.ui.state.ErrorType


sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val errorType: ErrorType) : Result<Nothing>()
}
