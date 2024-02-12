package com.sharpedge.currencyconverter.ui.state

sealed class ErrorType {
    object None : ErrorType()
    object NetworkError : ErrorType()
    object NotFoundError : ErrorType()
    object ServerError : ErrorType()
    object UnknownError : ErrorType()
    data class Custom(val message: String) : ErrorType()
    data class DatabaseError(val message: String) : ErrorType()

}