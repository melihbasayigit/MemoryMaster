package com.example.memorymaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import com.example.memorymaster.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth

        binding.button.setOnClickListener {
            addMovement(31,"A4","F1")
        }



    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val currentUser = auth.currentUser
    }

    fun login(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    println("GETIR")
                } else {
                    println("NOT SUCCESFUL")
                }
            }
    }

    private fun addMovement(playerId: Int, selectionFirst:String, selectionSecond: String) {
        val db = Firebase.firestore

        val movement = hashMapOf(
            "playerID" to playerId,
            "firstSelection" to selectionFirst,
            "secondSelection" to selectionSecond
        )

        db.collection("movement").add(movement).addOnSuccessListener { documentRef ->
            Log.d("melih", "Movement added with id $documentRef")
        }
            .addOnFailureListener { e ->
                Log.w("melih", "error: movement")
            }



    }

}