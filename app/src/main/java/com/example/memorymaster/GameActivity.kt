package com.example.memorymaster

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Base64
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memorymaster.databinding.ActivityGameBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayOutputStream
import kotlin.random.Random
import kotlin.time.Duration

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private var difficulty: Int? = null
    private val gryffindorCards = ArrayList<Card>()
    private val ravenclawCards = ArrayList<Card>()
    private val slytherinCards = ArrayList<Card>()
    private val hufflepuffCards = ArrayList<Card>()
    private val cardList = ArrayList<Card>()
    private val allCards = ArrayList<Card>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        difficulty = intent.getIntExtra("difficulty", 1)
        val spanCount = 2 * difficulty!!
        binding.recyclerViewGameArea.layoutManager = GridLayoutManager(this, spanCount)
        getCards()
    }


    private fun getCards() {
        val db = Firebase.firestore
        db.collection("cards").get().addOnSuccessListener { result ->
            for (document in result) {
                val card = Card(
                    document.id.toInt(),
                    document.data["cardName"].toString(),
                    document.data["cardHouse"].toString(),
                    document.data["cardPoint"].toString().toInt(),
                    document.data["cardImage"].toString(),
                    false
                )
                allCards.add(card)
            }

            for (card in allCards) {
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
            allCards.shuffle()
            slytherinCards.shuffle()
            gryffindorCards.shuffle()
            ravenclawCards.shuffle()
            hufflepuffCards.shuffle()
            when (difficulty) {
                1 -> {
                    for (i in 0..1) {
                        cardList.add(allCards[i])
                        allCards.removeAt(i)
                    }
                }
                2 -> {
                    for (i in 0..1) {
                        cardList.add(gryffindorCards[i])
                        gryffindorCards.removeAt(i)
                    }
                    for (i in 0..1) {
                        cardList.add(ravenclawCards[i])
                        ravenclawCards.removeAt(i)
                    }
                    for (i in 0..1) {
                        cardList.add(slytherinCards[i])
                        slytherinCards.removeAt(i)
                    }
                    for (i in 0..1) {
                        cardList.add(hufflepuffCards[i])
                        hufflepuffCards.removeAt(i)
                    }
                }
                else -> {
                    for (i in 0..4) {
                        cardList.add(gryffindorCards[i])
                        gryffindorCards.removeAt(i)
                    }
                    for (i in 0..3) {
                        cardList.add(ravenclawCards[i])
                        ravenclawCards.removeAt(i)
                    }
                    for (i in 0..4) {
                        cardList.add(slytherinCards[i])
                        slytherinCards.removeAt(i)
                    }
                    for (i in 0..3) {
                        cardList.add(hufflepuffCards[i])
                        hufflepuffCards.removeAt(i)
                    }
                }
            }
            cardList += cardList
            cardList.shuffle()
            //
            val adapterCard = GameCardAdapter(cardList,this, this@GameActivity)
            binding.recyclerViewGameArea.adapter = adapterCard
        }
            .addOnFailureListener { e ->
                Log.d("melih", "There is no document ${e.message}")
            }
    }

    /*
    private fun addCardsDb(cards: ArrayList<Card>) {
        val db = Firebase.firestore
        var counter = 0
        for (card in cards) {
            val cardHash = hashMapOf(
                "cardName" to card.cardName,
                "cardHouse" to card.cardHouse,
                "cardImage" to card.cardImage,
                "cardPoint" to card.cardPoint
            )

            db.collection("cards").document(card.cardId.toString())
                .set(cardHash)
                .addOnSuccessListener {
                    counter++
                    Log.d("melih", "$counter Card add successfully")

                }
                .addOnFailureListener { e ->
                    Log.d("melih", "Problem occured ${e.stackTrace}")
                }
        }
    }

    private fun encodeImage(bm: Bitmap?): String? {
        val baos = ByteArrayOutputStream()
        bm?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }*/

}
