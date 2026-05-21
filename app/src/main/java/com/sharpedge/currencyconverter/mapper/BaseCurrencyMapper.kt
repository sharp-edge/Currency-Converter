package com.sharpedge.currencyconverter.mapper

import com.sharpedge.currencyconverter.model.BaseCurrencyApiResponseModel
import com.sharpedge.currencyconverter.ui.model.BaseCurrencyUIModel


fun BaseCurrencyApiResponseModel.toUIModel(): BaseCurrencyUIModel {
    return BaseCurrencyUIModel(

        rates = this.rates,

    )
}
// Updated: 2026-05-21
