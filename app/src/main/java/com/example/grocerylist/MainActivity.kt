package com.example.grocerylist

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var sqLitehelper: SQLitehelper;
    private lateinit var mainrecyclerView: RecyclerView;
    private var adapter: MainListViewAdapter? = null;
    //for navigation view
    lateinit var drawerLayout : DrawerLayout;
    lateinit var navigationView: NavigationView;
    lateinit var toolbar: androidx.appcompat.widget.Toolbar;
    var isNightModeOn : Boolean = false;
    lateinit var sharedprefEditor: SharedPreferences.Editor;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //shared pref for night mode
        val appSettingPrefs : SharedPreferences = getSharedPreferences("AppSettingPrefs",0)
        sharedprefEditor = appSettingPrefs.edit()
        isNightModeOn = appSettingPrefs.getBoolean("NightMode",false)
        if(isNightModeOn)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


        findViewById<TextView>(R.id.createlist).setOnClickListener{
            val intent = Intent(this@MainActivity, CreateList::class.java)
            startActivity(intent)
        }
        //hooks
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)

        // tool bar
        setSupportActionBar(toolbar)

        //navigation drawer menu
        navigationView.bringToFront();
        var toggle :ActionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this)



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

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater : MenuInflater = menuInflater;
        inflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        var id = item.itemId;
        if(id == R.id.ldmode)
        {
            Log.d("navigation","Mode changed")
            if(isNightModeOn)
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                sharedprefEditor.putBoolean("NightMode",false);
                sharedprefEditor.apply();
            }
            else
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                sharedprefEditor.putBoolean("NightMode",true);
                sharedprefEditor.apply();
            }

        }
        else if(id == R.id.importdata)
        {
            Log.d("navigation","Data imported")
        }
        else if(id == R.id.exportdata)
        {
            Log.d("navigation","Data exported")
        }
        else if(id == R.id.favouritelist)
        {
            Log.d("navigation","favourite list")
        }
        return true;
    }



}