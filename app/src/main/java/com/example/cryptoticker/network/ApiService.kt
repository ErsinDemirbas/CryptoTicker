package com.example.cryptoticker.network

import com.example.cryptoticker.model.CryptoModel
import com.example.cryptoticker.model.CryptoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("coins/markets")
    fun getAllData(
        @Query("vs_currency") currencyType : String,
    ) : Single<List<CryptoModel>>

    @GET("coins/{id}")
    fun getData(
        @Path("id") id : String
    ) : Single<CryptoResponse>


}