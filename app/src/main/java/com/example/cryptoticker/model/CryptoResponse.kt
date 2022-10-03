package com.example.cryptoticker.model

import com.google.gson.annotations.SerializedName

data class CryptoResponse (
    @SerializedName("name")
    var name : String,
    @SerializedName("hashing_algorithm")
    var hashingAlgorithm : String,
    @SerializedName("image")
    var image : ImageType?,
    @SerializedName("description")
    var description : Language?,
    @SerializedName("market_data")
    var marketData : CurrentPrice,
    @SerializedName("price_change_percentage_24h")
    var changedPrice : Double,

)