package com.example.memorymaster.activity

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.memorymaster.adapter.GameCardAdapter
import com.example.memorymaster.databinding.ActivityGameBinding
import com.example.memorymaster.model.Card
import com.example.memorymaster.model.MediaPlayer
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private var difficulty: Int? = null
    private val gryffindorCards = ArrayList<Card>()
    private val ravenclawCards = ArrayList<Card>()
    private val slytherinCards = ArrayList<Card>()
    private val hufflepuffCards = ArrayList<Card>()
    private val cardList = ArrayList<Card>()
    private val allCards = ArrayList<Card>()
    private var multi: Boolean? = null
    private val mediaPlayer: MediaPlayer = MediaPlayer(this@GameActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        difficulty = intent.getIntExtra("difficulty", 1)
        multi = intent.getBooleanExtra("multi", false)
        Log.d("melih", multi.toString())
        val spanCount = 2 * difficulty!!
        binding.recyclerViewGameArea.layoutManager = GridLayoutManager(this, spanCount)
        getCards()
        mediaPlayer.playMainSoundtrack()
        binding.muteButtonGameActivity.setOnClickListener {
            mediaPlayer.mutePlayer()
        }
    }

    override fun onDestroy() {
        mediaPlayer.stopPlayer()
        super.onDestroy()
    }
    private fun createHouseCardLists() {
        for (card in allCards) {
            if (card.cardHouse.lowercase() == "hufflepuff") {
                hufflepuffCards.add(card)
            } else if (card.cardHouse.lowercase() == "gryffindor") {
                gryffindorCards.add(card)
            } else if (card.cardHouse.lowercase() == "ravenclaw") {
                ravenclawCards.add(card)
            } else {
                slytherinCards.add(card)
            }
        }
    }
    private fun shuffleCardLists() {
        allCards.shuffle()
        slytherinCards.shuffle()
        gryffindorCards.shuffle()
        ravenclawCards.shuffle()
        hufflepuffCards.shuffle()
    }

    private fun prepareCardListByDifficulty() {
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
    }

    private fun prepareGameAdapter() {
        val adapterCard = GameCardAdapter(cardList, this, this@GameActivity,
            multi, mediaPlayer)
        binding.recyclerViewGameArea.adapter = adapterCard
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

            createHouseCardLists()
            shuffleCardLists()
            prepareCardListByDifficulty()
            cardList += cardList
            cardList.shuffle()
            //
            val map = hashMapOf<String, Any>()
            var counter = 0
            var longString = ""
            cardList.forEach {
                map[counter.toString()] = "${it.cardId} -> ${it.cardName} ${it.cardHouse} "
                longString += "$counter -> ${it.cardName} ${it.cardHouse} ${it.cardId}\n"
                counter++
            }
            prepareGameAdapter()
            db.collection("area").document("last_game").set(map)
                .addOnFailureListener { e -> Log.d("melih", e.message.toString()) }
                .addOnSuccessListener { Log.d("melih", "Everything is ok 1.") }

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")
            val current = LocalDateTime.now().format(formatter)

            db.collection("area").document(current).set(map)
                .addOnSuccessListener { Log.d("melih", "Everything is ok 2.") }
                .addOnFailureListener { e -> Log.d("melih", "There is no document ${e.message}") }

        }

    }


    // for admin
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
                    Log.d("melih", "Problem occurred ${e.stackTrace}")
                }
        }
    }

    //for admin
    private fun encodeImage(bm: Bitmap?): String? {
        val baos = ByteArrayOutputStream()
        bm?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

}
