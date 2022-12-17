package com.example.memorymaster

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memorymaster.databinding.ActivityGameBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.ByteArrayOutputStream
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private var difficulty: Int = 1
    val gryffindorCards = ArrayList<Card>()
    val ravenclawCards = ArrayList<Card>()
    val slytherinCards = ArrayList<Card>()
    val hufflepuffCards = ArrayList<Card>()
    val cardList = ArrayList<Card>()
    val allCards = ArrayList<Card>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        difficulty = intent.getIntExtra("difficulty", 1)
        Log.d("melih", "burya giriyor mu")
        //getCards()
        val cardSendList = ArrayList<Card>()
        //




        var bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_albus_dumbledore)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(1,"Albus Dumbledore", "Gryffindor",20, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_rubeus_hagrid)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(2,"Rubeus Hagrid", "Gryffindor",12, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_minerva_mcgonagall)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(3,"Minerva McGonagall", "Gryffindor",13, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_arthur_weasley)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(4,"Arthur Weasley", "Gryffindor",10, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_sirius_black)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(5,"Sirius Black", "Gryffindor",18, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_lily_potter)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(6,"Lily Potter", "Gryffindor",12, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_remus_lupin)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(7,"Remus Lupin", "Gryffindor",10, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_peter_pettigrew)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(8,"Peter Pettigrew", "Gryffindor",5, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_harry_potter)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(9,"Harry Potter", "Gryffindor",10, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_ron_weasley)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(10,"Ron Weasley", "Gryffindor",8, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_hermione_granger)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(11,"Hermione Granger", "Gryffindor",10, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_rowena_ravenclaw)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(12,"Rowena Ravenclaw", "Ravenclaw",20, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_luna_lovegood)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(13,"Luna Lovegood", "Ravenclaw",9, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_gilderoy_lockhart)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(14,"Gilderoy Lockhart", "Ravenclaw",13, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_filius_flitwick)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(15,"Filius Flitwick", "Ravenclaw",10, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_cho_chang)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(16,"Cho Chang", "Ravenclaw",11, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_sybill_trelawney)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(17,"Sybill Trelawney", "Ravenclaw",14, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_marcus_belby)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(18,"Marcus Belby", "Ravenclaw",10, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_myrtle_warren)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(19,"Myrtle Warren", "Ravenclaw",5, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_padma_patil)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(20,"Myrtle Warren", "Ravenclaw",10, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_quirinus_quirrell)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(21,"Quirinus Quirrell", "Ravenclaw",15, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_garrick_ollivander)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(22,"Garrick Ollivander", "Ravenclaw",15, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_tom_riddle)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(23,"Tom Riddle", "Slytherin",20, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_horace_slughorn)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(24,"Horace Slughorn", "Slytherin",12, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_bellatrix_lestrange)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(25,"Bellatrix Lestrange", "Slytherin",13, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_narcissa_malfoy)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(26,"Narcissa Malfoy", "Slytherin",10, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_andromeda_tonks)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(27,"Andromeda Tonks", "Slytherin",16, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_lucius_malfoy)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(28,"Lucius Malfoy", "Slytherin",12, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_evan_rosier)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(29,"Evan Rosier", "Slytherin",10, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_draco_malfoy)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(30,"Draco Malfoy", "Slytherin",5, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_dolores_umbridge)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(31,"Dolores Umbridge", "Slytherin",10, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_severus_snape)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(32,"Severus Snape", "Slytherin",18, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_leta_lestrange)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(33,"Leta Lestrange", "Slytherin",10, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_helga_hufflepuff)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(34,"Helga Hufflepuff", "Hufflepuff",20, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_cedric_diggory)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(35,"Cedric Diggory", "Hufflepuff",18, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_nymphadora_tonks)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(36,"Nymphadora Tonks", "Hufflepuff",14, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_pomona_sprout)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(37,"Pomona Sprout", "Hufflepuff",10, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_newt_scamander)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(38,"Newt Scamander", "Hufflepuff",18, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_fat_friar)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(39,"Fat Friar", "Hufflepuff",12, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_hannah_abbott)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(40,"Hannah Abbott", "Hufflepuff",10, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_ernest_macmillan)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(41,"Ernest Macmillan", "Hufflepuff",5, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_leanne)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(42,"Leanne", "Hufflepuff",10, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_silvanus_kettleburn)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(43,"Silvanus Kettleburn", "Hufflepuff",12, base64)
            cardSendList.add(card)
        }

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.card_ted_lupin)
        encodeImage(bitmap)?.let { base64 ->
            val card = Card(44,"Ted Lupin", "Hufflepuff",10, base64)
            cardSendList.add(card)
        }



        //
        addCardsDb(cardSendList)
        //
        val adapter = GameCardAdapter(cardList)
        binding.recyclerViewGameArea.adapter = adapter

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
                    document.data["cardImage"].toString()
                )
                cardList.add(card)
                Log.d("melih", "${card.cardId} ${card.cardHouse} ${card.cardName}, ${card.cardPoint} ${card.cardImage}")
            }
        }
            .addOnFailureListener { e ->
                Log.d("melih", "There is no document")
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
                    Log.d("melih", cardList[i].cardName)
                    allCards.removeAt(i)
                }
            }
            2 -> {
                for (i in 0..1) {
                    cardList.add(gryffindorCards[i])
                    Log.d("melih", cardList[i].cardName)
                    gryffindorCards.removeAt(i)
                }
                for (i in 0..1) {
                    cardList.add(ravenclawCards[i])
                    Log.d("melih", cardList[i].cardName)
                    ravenclawCards.removeAt(i)
                }
                for (i in 0..1) {
                    cardList.add(slytherinCards[i])
                    Log.d("melih", cardList[i].cardName)
                    slytherinCards.removeAt(i)
                }
                for (i in 0..1) {
                    cardList.add(hufflepuffCards[i])
                    Log.d("melih", cardList[i].cardName)
                    hufflepuffCards.removeAt(i)
                }
            }
            else -> {
                for (i in 0..4) {
                    cardList.add(gryffindorCards[i])
                    Log.d("melih", cardList[i].cardName)
                    gryffindorCards.removeAt(i)
                }
                for (i in 0..3) {
                    cardList.add(ravenclawCards[i])
                    Log.d("melih", cardList[i].cardName)
                    ravenclawCards.removeAt(i)
                }
                for (i in 0..4) {
                    cardList.add(slytherinCards[i])
                    Log.d("melih", cardList[i].cardName)
                    slytherinCards.removeAt(i)
                }
                for (i in 0..3) {
                    cardList.add(hufflepuffCards[i])
                    Log.d("melih", cardList[i].cardName)
                    hufflepuffCards.removeAt(i)
                }
            }
        }
    }

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
    }

}