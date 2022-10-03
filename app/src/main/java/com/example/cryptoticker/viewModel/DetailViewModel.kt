package com.example.cryptoticker.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.cryptoticker.R
import com.example.cryptoticker.data.CryptoDatabase
import com.example.cryptoticker.model.CryptoModel
import com.example.cryptoticker.model.CryptoResponse
import com.example.cryptoticker.network.RetrofitInstance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private var db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    private val retrofitInstance = RetrofitInstance()
    private val disposable: CompositeDisposable = CompositeDisposable()
    private val coinsHashMap = hashMapOf<String, Any>()
    val cryptoCoin = MutableLiveData<CryptoResponse>()
    val cryptoDetailProgressBar = MutableLiveData<Boolean>()

    fun getDataFromApi(id: String) {
        cryptoDetailProgressBar.value = true

        disposable.add(
            retrofitInstance.getDataFromApi(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<CryptoResponse>() {
                    override fun onSuccess(t: CryptoResponse) {
                        cryptoCoin.value = t
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    fun setDataToFirestore(navController: NavController, id: String) {
        auth = FirebaseAuth.getInstance()
        val dataAccessObject = CryptoDatabase(getApplication()).cryptoDao()
        disposable.add(
            dataAccessObject.getDataById(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<CryptoModel>() {
                    override fun onSuccess(t: CryptoModel) {
                        coinsHashMap["userId"] = auth.currentUser!!.uid
                        coinsHashMap["id"] = t.id!!
                        coinsHashMap["name"] = t.name!!
                        coinsHashMap["symbol"] = t.symbol!!
                        coinsHashMap["price"] = t.price!!
                        saveToFirestore(navController, auth.currentUser!!.uid, t.id!!, coinsHashMap)
                    }
                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )

    }

    private fun saveToFirestore(navController: NavController, userId : String, id : String, hashMap: HashMap<String, Any>){

        db.collection("FavoriteCoins").whereEqualTo("userId", userId).whereEqualTo("id", id).get()
            .addOnSuccessListener {
                if (it != null) {
                    if (it.isEmpty) {
                        db.collection("FavoriteCoins").add(hashMap)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    navController.navigate(R.id.homeFragment)
                                }
                            }.addOnFailureListener { exception ->
                                Toast.makeText(
                                    getApplication(),
                                    exception.localizedMessage,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                    } else {
                        Toast.makeText(
                            getApplication(),
                            "Already added!",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    }
                }
            }

}