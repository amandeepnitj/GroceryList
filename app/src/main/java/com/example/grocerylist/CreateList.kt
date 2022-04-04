package com.example.grocerylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class CreateList : AppCompatActivity() {
    private lateinit var listname: EditText;
    private lateinit var itemname : EditText
    private lateinit var quantity : EditText;
    private  lateinit var cost : EditText;
    private lateinit var addbutton :ImageView;
    private lateinit var sqLitehelper: SQLitehelper;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_list)

        findViewById<ImageButton>(R.id.newlist_backbutton).setOnClickListener{
            val intent = Intent(this@CreateList, MainActivity::class.java)
            startActivity(intent)
        }



        findViewById<TextView>(R.id.newlist_done).setOnClickListener{
            val intent = Intent(this@CreateList, MainActivity::class.java)
            startActivity(intent)
        }

        initView();
        sqLitehelper = SQLitehelper(this);

        addbutton.setOnClickListener{
            adddata();
        }

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
                Toast.makeText(this,"data inserted",Toast.LENGTH_SHORT).show();
                clearFileds();
            }
            else
            {
                Toast.makeText(this, "Data is not inserted ",Toast.LENGTH_SHORT).show();

            }
        }


    }
    fun initView()
    {
        listname = findViewById(R.id.listname);
        itemname = findViewById(R.id.itemname);
        quantity = findViewById(R.id.quantity);
        cost   = findViewById(R.id.cost);
        addbutton = findViewById(R.id.addbutton);

    }
    fun clearFileds()
    {
        itemname.setText("")
        quantity.setText("");
        cost.setText("");
        itemname.requestFocus();
    }
}