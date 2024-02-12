package com.sharpedge.currencyconverter.mapper

import com.sharpedge.currencyconverter.model.BaseCurrencyApiResponseModel
import com.sharpedge.currencyconverter.ui.model.BaseCurrencyUIModel


fun BaseCurrencyApiResponseModel.toUIModel(): BaseCurrencyUIModel {
    return BaseCurrencyUIModel(
        baseCurrency = this.base,
        date = this.date,
        rates = this.rates,
        isSuccess = this.success,
        message = if (this.success) null else this.message
    )
}