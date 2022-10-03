package com.example.cryptoticker.network

import com.example.cryptoticker.model.CryptoModel
import com.example.cryptoticker.model.CryptoResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object{
        private const val BASE_URL = "https://api.coingecko.com/api/v3/"

        val cryptoApi: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }

    fun getAllDataFromApi(currencyType : String) : Single<List<CryptoModel>> {
        return cryptoApi.getAllData(currencyType)
    }

    fun getDataFromApi(cryptoId : String) : Single<CryptoResponse> {
        return cryptoApi.getData(cryptoId)
    }

}