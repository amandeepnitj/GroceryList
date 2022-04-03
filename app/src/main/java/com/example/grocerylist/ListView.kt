package com.example.grocerylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListView : AppCompatActivity() {
    private lateinit var sqLitehelper: SQLitehelper;
    private lateinit var recyclerView: RecyclerView;
    private var adapter: ListviewAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)


        findViewById<ImageButton>(R.id.backbutton).setOnClickListener{
            val intent = Intent(this@ListView, MainActivity::class.java)
            startActivity(intent)
        }



        findViewById<TextView>(R.id.Done).setOnClickListener{
            val intent = Intent(this@ListView, MainActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.detailview).setOnClickListener{
            val intent = Intent(this@ListView, DetailView::class.java)
            startActivity(intent)
        }
        initView();
        initRecyclerview()
        sqLitehelper = SQLitehelper(this);
        getdata();

    }
    fun getdata()
    {

        val listdata: ArrayList<ListModel> = sqLitehelper.getallData();
        Log.e("pppp","${listdata.size}")
        adapter?.addItems(listdata)


        // need to display data in recycler view

    }

    fun initView()
    {
//        itemname = findViewById(R.id.itemname);
//        quantity = findViewById(R.id.quantity);
//        cost = findViewById(R.id.cost);
        recyclerView = findViewById(R.id.list_recyclerview)
    }

    fun initRecyclerview()
    {
        recyclerView.layoutManager = LinearLayoutManager(this);
        adapter = ListviewAdapter()
        recyclerView.adapter = adapter
    }
}