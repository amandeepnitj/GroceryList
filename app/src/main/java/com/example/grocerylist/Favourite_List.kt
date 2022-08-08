package com.example.grocerylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Favourite_List : AppCompatActivity() {

    private lateinit var sqLitehelper: SQLitehelper;
    private lateinit var recyclerView: RecyclerView;

    private var adapter: FavouriteListAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_list)


        findViewById<TextView>(R.id.favourite_done).setOnClickListener{
            val intent = Intent(this@Favourite_List, MainActivity::class.java)
            startActivity(intent)
        }

        initView();
        initRecyclerview();

        sqLitehelper = SQLitehelper(this);
        getdata();



    }



    fun initView()
    {
//        itemname = findViewById(R.id.itemname);
//        quantity = findViewById(R.id.quantity);
//        cost = findViewById(R.id.cost);
        recyclerView = findViewById(R.id.favouritelist_recyclerview)

    }

    fun initRecyclerview()
    {
        recyclerView.layoutManager = LinearLayoutManager(this);
        adapter = FavouriteListAdapter()
        recyclerView.adapter = adapter
    }

    fun getdata()
    {

        val listdata: ArrayList<ListModel> = sqLitehelper.getfavouriteitems();
        Log.e("pppp","${listdata.size}")
        adapter?.addItems(listdata)


        // need to display data in recycler view

    }
}