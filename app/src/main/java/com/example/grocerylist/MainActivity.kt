package com.example.grocerylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
        adapter?.setOnClickItem {
            Toast.makeText(this, it,Toast.LENGTH_SHORT  ).show()
            val intent = Intent(this@MainActivity, ListView::class.java)
            var b :Bundle = Bundle();
            b.putString("listname",it);
            intent.putExtras(b);
            startActivity(intent)}



        adapter?.setOnOptionbuttonClick {
            Toast.makeText(this,it+ " option button clicked",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, EditList::class.java)
            var b :Bundle = Bundle();
            b.putString("listname",it);
            intent.putExtras(b);
            startActivity(intent)
        }
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