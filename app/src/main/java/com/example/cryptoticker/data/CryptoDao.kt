package com.example.cryptoticker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cryptoticker.model.CryptoModel
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptoDao {

    @Query("SELECT * FROM crypto_table ORDER BY specialKey ASC")
    fun getAllData() : List<CryptoModel>


    @Insert
    fun insertAllCryptoCoins(vararg crypto: CryptoModel) : List<Long>

    @Query("DELETE FROM crypto_table")
    fun deleteAllCoins()

    @Query("SELECT * FROM crypto_table WHERE id = :id")
    fun getDataById(id : String) : Single<CryptoModel>


    @Query("SELECT * FROM crypto_table WHERE name LIKE :searchQuery OR symbol LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<CryptoModel>>
}