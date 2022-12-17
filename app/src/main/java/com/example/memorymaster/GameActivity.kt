package com.example.memorymaster

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memorymaster.databinding.ActivityGameBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private var difficulty: Int = 1
    val gryffindorCards = ArrayList<Card>()
    val ravenclawCards = ArrayList<Card>()
    val slytherinCards = ArrayList<Card>()
    val hufflepuffCards = ArrayList<Card>()
    val cardList = ArrayList<Card>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        difficulty = intent.getIntExtra("difficulty", 1)
        Log.d("melih", "burya giriyor mu")



        val adapter = GameCardAdapter(cardList)
        binding.recyclerViewGameArea.adapter = adapter

    }

    private fun getCards() {
        val db = Firebase.firestore
        var cards = ArrayList<Card>()
        db.collection("cards").get().addOnSuccessListener { result ->
            for (document in result) {
                val card = Card(
                    document.id.toInt(),
                    document.data["cardName"].toString(),
                    document.data["cardHouse"].toString(),
                    document.data["cardPoint"].toString().toInt(),
                    document.data["cardImage"].toString()
                )
                cards.add(card)
                Log.d("melih", "${card.cardId} ${card.cardHouse} ${card.cardName}, ${card.cardPoint} ${card.cardImage}")
            }
        }
            .addOnFailureListener { e ->
                Log.d("melih", "There is no document")
            }
        for (card in cards) {
            if (card.cardHouse.lowercase() == "hufflepuff") {
                hufflepuffCards.add(card)
            }
            else if (card.cardHouse.lowercase() == "gryffindor") {
                gryffindorCards.add(card)
            }
            else if (card.cardHouse.lowercase() == "ravenclaw") {
                ravenclawCards.add(card)
            } else {
                slytherinCards.add(card)
            }
        }

        when (difficulty) {
            1 -> {
                for (i in 1..2) {
                    val cardRandomIndex = Random.nextInt(cards.size)
                    cardList.add(cards[cardRandomIndex])
                    Log.d("melih", cardList[cardRandomIndex].cardName)
                    cards.removeAt(cardRandomIndex)
                }
            }
            2 -> {
                for (i in 1..2) {
                    val cardRandomIndex = Random.nextInt(gryffindorCards.size)
                    cardList.add(gryffindorCards[cardRandomIndex])
                    Log.d("melih", cardList[cardRandomIndex].cardName)
                    gryffindorCards.removeAt(cardRandomIndex)
                }
                for (i in 1..2) {
                    val cardRandomIndex = Random.nextInt(ravenclawCards.size)
                    cardList.add(ravenclawCards[cardRandomIndex])
                    Log.d("melih", cardList[cardRandomIndex].cardName)
                    ravenclawCards.removeAt(cardRandomIndex)
                }
                for (i in 1..2) {
                    val cardRandomIndex = Random.nextInt(slytherinCards.size)
                    cardList.add(slytherinCards[cardRandomIndex])
                    Log.d("melih", cardList[cardRandomIndex].cardName)
                    slytherinCards.removeAt(cardRandomIndex)
                }
                for (i in 1..2) {
                    val cardRandomIndex = Random.nextInt(hufflepuffCards.size)
                    cardList.add(hufflepuffCards[cardRandomIndex])
                    Log.d("melih", cardList[cardRandomIndex].cardName)
                    hufflepuffCards.removeAt(cardRandomIndex)
                }
            }
            else -> {
                for (i in 1..5) {
                    val cardRandomIndex = Random.nextInt(gryffindorCards.size)
                    cardList.add(gryffindorCards[cardRandomIndex])
                    Log.d("melih", cardList[cardRandomIndex].cardName)
                    gryffindorCards.removeAt(cardRandomIndex)
                }
                for (i in 1..4) {
                    val cardRandomIndex = Random.nextInt(ravenclawCards.size)
                    cardList.add(ravenclawCards[cardRandomIndex])
                    Log.d("melih", cardList[cardRandomIndex].cardName)
                    ravenclawCards.removeAt(cardRandomIndex)
                }
                for (i in 1..5) {
                    val cardRandomIndex = Random.nextInt(slytherinCards.size)
                    cardList.add(slytherinCards[cardRandomIndex])
                    Log.d("melih", cardList[cardRandomIndex].cardName)
                    slytherinCards.removeAt(cardRandomIndex)
                }
                for (i in 1..4) {
                    val cardRandomIndex = Random.nextInt(hufflepuffCards.size)
                    cardList.add(hufflepuffCards[cardRandomIndex])
                    Log.d("melih", cardList[cardRandomIndex].cardName)
                    hufflepuffCards.removeAt(cardRandomIndex)
                }
            }
        }


    }

    private fun readCard(cardId: Int) {
        val db = Firebase.firestore
        val docRef = db.collection("cards").document(cardId.toString())
        docRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.data != null) {
                Log.d("melih", documentSnapshot.data!!["cardHouse"] as String)
            }
        }
    }

}