package com.example.cryptoticker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "crypto_table")
data class CryptoModel (
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id : String?,
    @ColumnInfo(name = "symbol")
    @SerializedName("symbol")
    var symbol : String?,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name : String?,

    @ColumnInfo(name = "price")
    @SerializedName("current_price")
    var price : Double?
){
    @PrimaryKey(autoGenerate = true)
    var specialKey : Int = 0
}