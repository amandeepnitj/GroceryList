package com.example.grocerylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListView : AppCompatActivity() {
    private lateinit var sqLitehelper: SQLitehelper;
    private lateinit var recyclerView: RecyclerView;
//    private lateinit var ItemListview : LinearLayout;
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
        initRecyclerview();
        var b : Bundle? = intent.extras;
        var value :String = "";
        if(b!= null)
        {
            value = b.getString("listname").toString();

        }
        findViewById<TextView>(R.id.ListName).setText(value);
        sqLitehelper = SQLitehelper(this);
        getdata(value);


        adapter?.setOnClickItemlistview{
            Toast.makeText(this,it.id.toString()  +"  Linear View clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@ListView, ListView::class.java)
            sqLitehelper.itemstatuschange(it.id);
            var b :Bundle = Bundle();
            b.putString("listname",it.listname);
            intent.putExtras(b);
            startActivity(intent)
        }

    }
    fun getdata(value: String)
    {

        val listdata: ArrayList<ListModel> = sqLitehelper.getallData(value);
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