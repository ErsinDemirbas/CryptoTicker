package com.example.cryptoticker.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoticker.model.CryptoModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FavoriteViewModel : ViewModel() {

    private val db = Firebase.firestore
    private lateinit var auth : FirebaseAuth

    val favoriteCoins = MutableLiveData<List<CryptoModel>>()
    val favoriteProgressBar = MutableLiveData<Boolean>()
    var favoriteList = ArrayList<CryptoModel>()

    fun getAllDataFromFireStore(context : Context){
        auth = FirebaseAuth.getInstance()
        favoriteProgressBar.value = true
        db.collection("FavoriteCoins").whereEqualTo("userId", auth.currentUser?.uid).addSnapshotListener { value, error ->
            if (error != null){
                Toast.makeText(context,"yess", Toast.LENGTH_LONG).show()
            }else{
                if (value != null){
                    if (!value.isEmpty){
                        val documents = value.documents
                        favoriteList.clear()
                        for (document in documents){
                            val id = document.get("id") as String
                            val name = document.get("name") as String
                            val symbol = document.get("symbol") as String
                            val price = document.get("price") as Double

                            val downloadedData = CryptoModel(id, symbol, name, price)
                            favoriteList.add(downloadedData)
                        }
                        favoriteCoins.value = favoriteList
                    }else{
                        favoriteProgressBar.value = false
                    }
                }
            }
        }


    }


}