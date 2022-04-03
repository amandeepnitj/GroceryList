package com.example.grocerylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class DetailView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_view)



        findViewById<ImageButton>(R.id.detail_backbutton).setOnClickListener{
            val intent = Intent(this@DetailView, MainActivity::class.java)
            startActivity(intent)
        }



        findViewById<TextView>(R.id.detail_done).setOnClickListener{
            val intent = Intent(this@DetailView, MainActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.detail_listview).setOnClickListener{
            val intent = Intent(this@DetailView, ListView::class.java)
            startActivity(intent)
        }
    }
}