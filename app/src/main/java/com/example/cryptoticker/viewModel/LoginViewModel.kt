package com.example.cryptoticker.viewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.cryptoticker.view.MainActivity
import com.example.cryptoticker.view.fragments.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.actionCodeSettings

class LoginViewModel : ViewModel() {

    private lateinit var auth: FirebaseAuth

    fun registerWithEmailAndPassword(context: Context, email: String, password : String){
        auth = FirebaseAuth.getInstance()
        if (email != "" && password != ""){
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(context, "Register successful!", Toast.LENGTH_LONG).show()
                    val action = Intent(context, MainActivity::class.java)
                    context.startActivity(action)
                    val activity = context as Activity
                    activity.finish()
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(context, "Please fill in the blanks!", Toast.LENGTH_LONG).show()
        }

    }

    fun signInWithEmailAndPassword(context: Context, email: String, password : String){
        auth = FirebaseAuth.getInstance()
        if (email != "" && password != ""){
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(context, "Login successful!", Toast.LENGTH_LONG).show()
                    val action = Intent(context, MainActivity::class.java)
                    context.startActivity(action)
                    val activity = context as Activity
                    activity.finish()
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(context, "Please fill in the blanks!", Toast.LENGTH_LONG).show()
        }

    }

}