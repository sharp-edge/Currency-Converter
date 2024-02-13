package com.sharpedge.currencyconverter.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("HH:mm      dd-MM-yyyy", Locale.getDefault())
    return formatter.format(timestamp)
}
