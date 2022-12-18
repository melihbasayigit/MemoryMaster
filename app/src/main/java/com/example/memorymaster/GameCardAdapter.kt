package com.example.memorymaster

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memorymaster.databinding.RecyclerViewItemBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class GameCardAdapter(val cardList: ArrayList<Card>) :
    RecyclerView.Adapter<GameCardAdapter.GameCardHolder>() {
    var lastPosition = -1
    var pairCounter = 0
    var score = 0.0f
    var timeK = 0.5f


    class GameCardHolder(val binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameCardHolder {
        val binding =
            RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameCardHolder(binding)
    }

    override fun onBindViewHolder(holder: GameCardHolder, position: Int) {
        val timer = object: CountDownTimer(45000, 1000) {
            override fun onTick(milisUntilFinish: Long) {
                Log.d("melih", milisUntilFinish.toString())
            }
            override fun onFinish() {
                gameOver(holder)
            }
        }
        holder.binding.recyclerViewItemImageView.setImageResource(R.drawable.card_bg)
        holder.itemView.setOnClickListener {
            if (!cardList[position].lock) {
                if (lastPosition == position) {
                    holder.binding.recyclerViewItemImageView.setImageResource(R.drawable.card_bg)
                    lastPosition = -1
                } else {
                    if (lastPosition == -1) {
                        // first selection
                        lastPosition = position
                    } else {
                        // second selection
                        // pair
                        tim()
                        if (pair(position)) {
                            getPoint(position)
                        } else {
                            reducePoint(position)
                        }
                        pairCounter++
                        lastPosition = -1
                    }
                    holder.binding.recyclerViewItemImageView.setImageBitmap(
                        convertStringToImage(
                            cardList[position].cardImage
                        )
                    )
                }
            }

            if (pairCounter * 2 == cardList.size) {
                gameOver(holder)
            }
        }
    }

    private fun gameOver(holder: GameCardHolder) {
        Log.d("melih", "GAME OVER")
        val i = Intent(holder.itemView.context, MenuActivity::class.java)
        i.putExtra("score", score)
        holder.itemView.context.startActivity(i)
        holder.itemView.onFinishTemporaryDetach()
    }

    private fun getCardHouse(position: Int) :String {
        return cardList[position].cardHouse.lowercase()
    }

    private fun getCardPoint(position: Int) :Int {
        return cardList[position].cardPoint
    }

    private fun getCardHouseK(position: Int): Int {
        if (cardList[position].cardHouse.lowercase() == "gryffindor" || cardList[position].cardHouse.lowercase() == "slytherin") {
            return 2
        }
        return 1
    }

    private fun reducePoint(position: Int) {
        if (getCardHouse(position) == getCardHouse(lastPosition)) {
            var totalPoint:Float = (getCardPoint(position) + getCardPoint(lastPosition)).toFloat()
            val houseK = getCardHouseK(position)
            var point: Float = totalPoint / houseK
            point *= (timeK/10)
            score -= point
        } else {
            var totalPoint:Float = (getCardPoint(position) + getCardPoint(lastPosition)).toFloat() / 2
            var point = totalPoint * getCardHouseK(position) * getCardHouseK(lastPosition) * (timeK / 10)
            score -= point
        }
    }

    private fun convertStringToImage(base64String: String): Bitmap {
        val decodedString = android.util.Base64.decode(base64String, android.util.Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

    private fun tim() {
        var rands = (3..10).random()
        timeK = rands.toFloat()
        timeK /= 10
    }

    private fun pair(position: Int): Boolean {
        if (cardList[lastPosition].cardId == cardList[position].cardId) {
            return true
        }
        return false
    }


    private fun getPoint(position: Int) {
        var totalPoint = getCardPoint(position) * 2 * getCardHouseK(position)
        score += totalPoint
    }


}