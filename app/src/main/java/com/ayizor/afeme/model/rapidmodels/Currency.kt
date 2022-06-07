package com.ayizor.afeme.model.rapidmodels

import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("base_currency_code")
    val base_currency_code: Int? = null,
    @SerializedName("base_currency_name")
    val base_currency_name: String? = "",
    @SerializedName("amount")
    val amount: String? = "",
    @SerializedName("updated_date")
    val updated_date: String? = "",
    @SerializedName("status")
    val status: String? = ""
)
