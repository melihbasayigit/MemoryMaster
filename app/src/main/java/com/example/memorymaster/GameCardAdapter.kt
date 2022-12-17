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
    val cardId = -1
    val selectionLocked = false

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
        holder.itemView.setOnClickListener {
            holder.binding.recyclerViewItemImageView.setImageBitmap(convertStringToImage(cardList[position].cardImage))
        }
    }

    private fun convertStringToImage(base64String: String): Bitmap {
        val decodedString = android.util.Base64.decode(base64String, android.util.Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }


}