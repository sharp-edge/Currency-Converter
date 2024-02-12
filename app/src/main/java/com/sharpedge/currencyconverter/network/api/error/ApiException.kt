package com.sharpedge.currencyconverter.network.api.error

import com.sharpedge.currencyconverter.ui.state.ErrorType


class ApiException(val errorType: ErrorType) : Exception()
