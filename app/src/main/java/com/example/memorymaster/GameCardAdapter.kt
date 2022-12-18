package com.example.memorymaster

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memorymaster.databinding.RecyclerViewItemBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GameCardAdapter(val cardList: ArrayList<Card>): RecyclerView.Adapter<GameCardAdapter.GameCardHolder>() {
    var lastPosition = -1
    var pairCounter = 0

    class GameCardHolder(val binding: RecyclerViewItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameCardHolder {
        val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameCardHolder(binding)
    }

    override fun onBindViewHolder(holder: GameCardHolder, position: Int) {
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
                        if (pair(position)) {
                            getPoint()
                        } else {
                            reducePoint()
                        }
                        pairCounter++
                        lastPosition = -1
                    }
                    holder.binding.recyclerViewItemImageView.setImageBitmap(convertStringToImage(cardList[position].cardImage))
                }
            }
            if (pairCounter * 2 == cardList.size) {
                gameover()
            }
        }
    }

    private fun gameover() {
        Log.d("melih", "GAME OVER")
    }

    private fun reducePoint() {
        Log.d("melih", "puan kaybettin")
    }

    private fun convertStringToImage(base64String: String): Bitmap {
        val decodedString = android.util.Base64.decode(base64String, android.util.Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

    private fun pair(position:Int): Boolean {
        if (cardList[lastPosition].cardId == cardList[position].cardId) {
            return true
        }
        return false
    }


    private fun getPoint() {
        Log.d("melih", "puan aldin")
    }


}