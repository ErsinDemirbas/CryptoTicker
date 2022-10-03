package com.example.cryptoticker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptoticker.model.CryptoModel

@Database(
    entities = [CryptoModel::class], //Model class
    version = 1,
    exportSchema = false
)

abstract class CryptoDatabase : RoomDatabase(){

    abstract fun cryptoDao() : CryptoDao //Call Dao

    //Singleton
    companion object{

        //Volatile because we made it visible to different threads.
        @Volatile private var instance : CryptoDatabase? = null

          private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: createDatabase(context).also {
                instance = it
            }
        }


        private fun createDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext,
            CryptoDatabase::class.java,"myCryptoDb").build()
    }
}