package com.example.memorymaster

data class Card(val cardId:Int, val cardName:String, val cardHouse:String, val cardPoint:Int, val cardImage:String, var lock: Boolean)