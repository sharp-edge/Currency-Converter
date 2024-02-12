package com.sharpedge.currencyconverter.network.api.error

import com.google.gson.annotations.SerializedName

data class ErrorDetail(
    @SerializedName("code") val code: Int,
    @SerializedName("type") val type: String,
    @SerializedName("info") val info: String?
)
