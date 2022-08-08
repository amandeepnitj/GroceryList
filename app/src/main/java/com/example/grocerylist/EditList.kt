package com.example.grocerylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
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
    private lateinit var listname_edittext : EditText;
    private lateinit var storename : EditText;
    private lateinit var nextgrocerydate : EditText;
    var mylistname = "" ;

    private var adapter: EditListViewAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_list)

        findViewById<ImageButton>(R.id.editlist_backbutton).setOnClickListener{
            val intent = Intent(this@EditList, MainActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.editlist_done).setOnClickListener{
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
            mylistname =value;
            var list = sqLitehelper.getparticularitemwithlistname(value);
            storename.setText(list.storename);
            nextgrocerydate.setText(list.nextgrocerydate)



        }
        listname_edittext.setText(value);
        getdata(value);



        addbutton.setOnClickListener{
            adddata();
            val intent = Intent(this@EditList, EditList::class.java)
            var b :Bundle = Bundle();
            b.putString("listname",value);
            intent.putExtras(b);
            startActivity(intent)




        }
        deletelist_button.setOnClickListener()
        {
            Toast.makeText(this,"  delete button clicked",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@EditList, MainActivity::class.java)
            sqLitehelper.deleteWholeList(value);
            startActivity(intent)
        }


        adapter?.setOnClickDeleteButton {
            Toast.makeText(this,it.id.toString()+ " individual delete button clicked",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@EditList, EditList::class.java)
            sqLitehelper.deleteItem(it.id);
            var b :Bundle = Bundle();
            b.putString("listname",it.listname);
            intent.putExtras(b);
            startActivity(intent)
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
        nextgrocerydate = findViewById(R.id.nextgrocerydate_edit);
        storename = findViewById(R.id.storename_edit);

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
        val nextgrocerydate = nextgrocerydate.text.toString();
        val storename  = storename.text.toString();
        val favourite : Int =0;

        if(listname.isEmpty()||itemname.isEmpty()||quantity==0||cost==0f||nextgrocerydate.isEmpty()||storename.isEmpty())
        {
            Toast.makeText(this, "Please enter the data first   ", Toast.LENGTH_SHORT).show()
        }
        else{
            val list = ListModel(listname = listname,itemname = itemname,quantity = quantity,cost = cost,bought = bought,storename = storename,nextgrocerydate = nextgrocerydate,favourite = favourite);
            val status = sqLitehelper.insertData(list);
            if(status>-1)
            {
                Toast.makeText(this,"data inserted",Toast.LENGTH_SHORT).show();
                clearFields()

            }
            else
            {
                Toast.makeText(this, "Data is not inserted ",Toast.LENGTH_SHORT).show();

            }
        }


    }

    fun getdata(value: String)
    {

        val listdata: ArrayList<ListModel> = sqLitehelper.getallData(value);
        Log.e("pppp_get data updated","${listdata.size}")
        adapter?.additems(listdata)
    }
}