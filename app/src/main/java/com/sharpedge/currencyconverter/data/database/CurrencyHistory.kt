package com.sharpedge.currencyconverter.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "currency_records")
data class CurrencyHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fromCurrency: String,
    val toCurrency: String,
    val timestamp: Long // Store the timestamp of the conversion
)