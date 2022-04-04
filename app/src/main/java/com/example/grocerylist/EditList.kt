package com.example.grocerylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EditList : AppCompatActivity() {
    private lateinit var listname : EditText;
    private lateinit var deletelist_button : ImageView;
    private lateinit var recyclerView: RecyclerView;
    private lateinit var itemname : EditText;
    private lateinit var quantity : EditText
    private lateinit var cost : EditText
    private lateinit var addbutton : ImageView;
    private lateinit var sqLitehelper: SQLitehelper;
    private lateinit var listname_edittext : EditText

    private var adapter: EditListViewAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_list)

        findViewById<ImageButton>(R.id.editlist_backbutton).setOnClickListener{
            val intent = Intent(this@EditList, MainActivity::class.java)
            startActivity(intent)
        }


        initView();
        initRecyclerview();
        sqLitehelper = SQLitehelper(this);

        var b : Bundle? = intent.extras;
        var value :String = "";
        if(b!= null)
        {
            value = b.getString("listname").toString();

        }
        listname_edittext.setText(value);
        getdata(value);


        addbutton.setOnClickListener{
            adddata();
            getdata(value)
        }




    }

    fun initRecyclerview()
    {
        recyclerView.layoutManager = LinearLayoutManager(this);
        adapter = EditListViewAdapter()
        recyclerView.adapter = adapter
    }

    fun initView()
    {
        listname = findViewById(R.id.edit_listname);
        itemname = findViewById(R.id.editlist_itemname);
        quantity = findViewById(R.id.editlist_quantity);
        cost   = findViewById(R.id.editlist_cost);
        addbutton = findViewById(R.id.editlist_addbutton);
        recyclerView = findViewById(R.id.editlist_recyclerview)
        deletelist_button = findViewById(R.id.deletelist_button)
        listname_edittext = findViewById(R.id.edit_listname)

    }
    fun clearFields()
    {
        itemname.setText("")
        quantity.setText("");
        cost.setText("");
        itemname.requestFocus();
    }
    fun adddata()
    {
        val listname = listname.text.toString();
        val itemname = itemname.text.toString();
        val quantity = quantity.text.toString().toInt();
        val cost = cost.text.toString().toFloat();
        val bought : Int = 0
        if(listname.isEmpty()||itemname.isEmpty()||quantity==0||cost==0f)
        {
            Toast.makeText(this, "Please enter the data first   ", Toast.LENGTH_SHORT).show()
        }
        else{
            val list = ListModel(listname = listname,itemname = itemname,quantity = quantity,cost = cost,bought = bought);
            val status = sqLitehelper.insertData(list);
            if(status>-1)
            {
                Toast.makeText(this,"data inserted", Toast.LENGTH_SHORT).show();
                clearFields();
            }
            else
            {
                Toast.makeText(this, "Data is not inserted ", Toast.LENGTH_SHORT).show();

            }
        }

    }

    fun getdata(value: String)
    {

        val listdata: ArrayList<ListModel> = sqLitehelper.getallData(value);
        Log.e("pppp","${listdata.size}")
        adapter?.additems(listdata)
    }
}