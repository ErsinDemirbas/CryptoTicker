package com.example.cryptoticker.model

import com.google.gson.annotations.SerializedName

data class ImageType(
    @SerializedName("thumb")
    var thumb : String?,
    @SerializedName("small")
    var small : String?,
    @SerializedName("large")
    var large : String?
)
