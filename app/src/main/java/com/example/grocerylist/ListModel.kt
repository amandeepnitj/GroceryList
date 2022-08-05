package com.example.grocerylist

import java.util.*
import kotlin.random.Random as Random1

data class ListModel(
    var id: Int = getAutoid(),
    var listname: String ="",
    var itemname: String = "",
    var quantity: Int = 0,
    var cost: Float = 0f,
    var bought: Int = 0,
    var storename : String ="",
    var nextgrocerydate : String ="",
    var favourite : Int = 0

) {
    companion object{
        fun getAutoid() : Int
        {
            val random = Random();
            return random.nextInt(100);


        }
    }
}