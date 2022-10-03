package com.example.cryptoticker.model

import com.google.gson.annotations.SerializedName

data class CurrentPriceType(
    @SerializedName("usd")
    var usd : Double?,
)
