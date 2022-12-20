package com.example.memorymaster

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.memorymaster.databinding.RecyclerViewItemBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.FileReader
import kotlin.random.Random

class GameCardAdapter(
    val cardList: ArrayList<Card>,
    val gameActivity: AppCompatActivity,
    val parentActivity: GameActivity,
    val multi:Boolean?
) :
    RecyclerView.Adapter<GameCardAdapter.GameCardHolder>() {
    var lastPosition = -1
    var pairCounter = 0
    var score = 0.0f
    var context: Context? = null
    var timeText: TextView? = null
    var timerStart = false
    var remainTime: Long? = null
    val timeInitValue: Long = 45000

    private val baseTimer = object : CountDownTimer(timeInitValue, 1000) {
        override fun onTick(milisUntilFinish: Long) {
            remainTime = milisUntilFinish
            timeText?.text = "Time: ${remainTime!! / 1000}"
        }

        override fun onFinish() {
            gameOver()
        }
    }

    private var timer = baseTimer

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
        context = holder.itemView.context
        holder.binding.recyclerViewItemImageView.setImageResource(R.drawable.card_bg)
        val scoreText = gameActivity.findViewById<TextView>(R.id.textViewScore)
        timeText = gameActivity.findViewById(R.id.textViewTime)
        addDbArea()
        holder.binding.recyclerViewItemImageView.setOnClickListener {
            if (!timerStart) {
                timerStart = true

                timer.start()
            }
            if (lastPosition == -1) {
                lastPosition = position
            } else {
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
            scoreText.text = "Score: $score"
            if (pairCounter * 2 == cardList.size) {
                gameOver(holder)
            }
        }
    }


    private fun gameOver(holder: GameCardHolder) {
        timer.cancel()
        restartTimer()

        Log.d("melih", "GAME OVER")
        val i = Intent(holder.itemView.context, MenuActivity::class.java)
        i.putExtra("score", score)
        score = 0.0f
        lastPosition = -1
        holder.itemView.context.startActivity(i)
        parentActivity.finish()
    }

    private fun gameOver() {
        timer.cancel()
        restartTimer()
        Log.d("melih", "GAME OVER")
        val i = Intent(context, MenuActivity::class.java)
        i.putExtra("score", score)
        score = 0.0f
        lastPosition = -1
        context?.startActivity(i)
        parentActivity.finish()
    }

    private fun restartTimer() {
        timer = baseTimer
        timerStart = false
    }

    private fun getCardHouse(position: Int): String {
        return cardList[position].cardHouse.lowercase()
    }

    private fun getCardPoint(position: Int): Int {
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
            val totalPoint: Float = (getCardPoint(position) + getCardPoint(lastPosition)).toFloat()
            val houseK = getCardHouseK(position)
            var point: Float = totalPoint / houseK
            point *= ((timeInitValue - remainTime!!).toFloat() / 10000.0f)
            score -= point
        } else {
            val totalPoint: Float =
                (getCardPoint(position) + getCardPoint(lastPosition)).toFloat() / 2.0f
            val point =
                totalPoint * getCardHouseK(position) * getCardHouseK(lastPosition) * ((timeInitValue - remainTime!!).toFloat() / 10000.0f)
            score -= point
        }
    }

    private fun convertStringToImage(base64String: String): Bitmap {
        val decodedString = android.util.Base64.decode(base64String, android.util.Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }


    private fun pair(position: Int): Boolean {
        if (cardList[lastPosition].cardId == cardList[position].cardId) {
            return true
        }
        return false
    }


    private fun getPoint(position: Int) {
        val totalPoint =
            ((getCardPoint(position) * 2 * getCardHouseK(position)).toFloat()) * (this.remainTime?.div(10000)!!).toFloat()
        score += totalPoint
    }

    private fun addDbArea() {

    }

}
