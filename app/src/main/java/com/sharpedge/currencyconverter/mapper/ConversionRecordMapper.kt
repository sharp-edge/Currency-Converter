package com.sharpedge.currencyconverter.mapper


import com.sharpedge.currencyconverter.data.database.CurrencyHistory
import com.sharpedge.currencyconverter.ui.model.CurrencyRecordUIModel
import com.sharpedge.currencyconverter.utils.formatDate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun CurrencyHistory.toUIModel(): CurrencyRecordUIModel {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val formattedDate = sdf.format(Date(this.timestamp))
    return CurrencyRecordUIModel(
        fromCurrency = this.fromCurrency,
        toCurrency = this.toCurrency,
        date = formatDate(this.timestamp)
    )
}
