package com.sharpedge.currencyconverter.mapper

import com.sharpedge.currencyconverter.model.SymbolsApiResponseModel
import com.sharpedge.currencyconverter.ui.model.CurrencySymbolUIModel


fun SymbolsApiResponseModel.mapToUIModel(): List<CurrencySymbolUIModel> {
    return this.symbols.map { (code, name) ->
        CurrencySymbolUIModel(code, name)
    }
}