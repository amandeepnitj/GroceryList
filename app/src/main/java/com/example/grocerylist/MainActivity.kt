package com.example.grocerylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var sqLitehelper: SQLitehelper;
    private lateinit var mainrecyclerView: RecyclerView;
    private var adapter: MainListViewAdapter? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<TextView>(R.id.createlist).setOnClickListener{
            val intent = Intent(this@MainActivity, CreateList::class.java)
            startActivity(intent)
        }


        initViews();
        initrecyclerView();
        sqLitehelper = SQLitehelper(this);
        getmainlistdata();
    }

    fun getmainlistdata()
    {
        val listdata : ArrayList<String> = sqLitehelper.getlistnameData();
        Log.e("eeee","${listdata.size}");
        adapter?.addItems(listdata)

    }

    fun initViews()
    {
        mainrecyclerView = findViewById(R.id.list_recyclerviewmain);
    }

    fun initrecyclerView()
    {
        mainrecyclerView.layoutManager = LinearLayoutManager(this);
        adapter = MainListViewAdapter();
        mainrecyclerView.adapter = adapter;

    }

}