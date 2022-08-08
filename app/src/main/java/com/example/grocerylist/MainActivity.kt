package com.example.grocerylist

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior.getTag
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import java.io.*


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
    var requestcode = 1;
    var filepathvalue ="";


    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf<String>(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )



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
        Log.d("checking",Environment.getExternalStorageDirectory().getAbsolutePath()+"")
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
        // ----done

        //Notification for grocery date
//        createnotificationchannel()



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
            openfilechooser()

            Log.d("navigation","Data imported")



        }
        else if(id == R.id.exportdata)
        {
            Log.d("navigation","Data exported")
            val wp  = ActivityCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            val rp  = ActivityCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.READ_EXTERNAL_STORAGE);
            val mp  = ActivityCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.MANAGE_EXTERNAL_STORAGE);
            if(wp != PackageManager.PERMISSION_GRANTED || rp!= PackageManager.PERMISSION_GRANTED || mp!=PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
                );

                Log.d("granted","permission")
            }

            //start

            val listarray  = sqLitehelper.getfavouriteitems();
            val data = StringBuilder()

            var s :String = "\n";
            data.append("id,listname,itemname,quantity,cost,bought,favourite,storename,nextgrocerydate")
            for (i in listarray) {

                data.append(
                    """$s
            ${i.id},${i.listname},${i.itemname},${i.quantity},${i.cost},${i.bought},${i.favourite},${i.storename},${i.nextgrocerydate}
            """.trimIndent()
                )
            }

            try {
                val fos : FileOutputStream = openFileOutput("data.csv", MODE_PRIVATE)
                fos.write(data.toString().toByteArray());
                fos.close();
//                val out =
//                    FileOutputStream(Environment.getExternalStorageDirectory().absolutePath + "/grocerylist.csv")
//                out.write(data.toString().toByteArray())
//                out.close()


                val context = applicationContext
                val filelocation = File(filesDir, "data.csv")
                val path = FileProvider.getUriForFile(
                    context,
                    "com.example.grocerylist.MainActivity",
                    filelocation
                )
                val fileIntent = Intent(Intent.ACTION_SEND)
                fileIntent.type = "text/csv"
                fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data")
                fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                fileIntent.putExtra(Intent.EXTRA_STREAM, path)
                startActivity(Intent.createChooser(fileIntent, "Send mail"))
            } catch (e: Exception) {
                e.printStackTrace()
            }





//            val obj = FileHandling();
//            obj.export();
        }
        else if(id == R.id.favouritelist)
        {
            Toast.makeText(this, " Favourite list ",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, Favourite_List::class.java)
            startActivity(intent)
        }
        return true;
    }

    //notification code
    private fun createnotificationchannel() {
        val name = "Notif Channel";
        val desc = "A description of the channel";
        val importance =  NotificationManager.IMPORTANCE_HIGH;
        val channel = NotificationChannel(channelid,name,importance);
        channel.description=desc;
        val notificationManager  = getSystemService(NOTIFICATION_SERVICE) as NotificationManager;
        notificationManager.createNotificationChannel(channel);

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        var context : Context = applicationContext;
        Log.d("inside","no");
        if(requestcode== requestCode && resultCode == Activity.RESULT_OK)
        {
            Log.d("inside","yes");
            if(data != null)
            {
                var uri : Uri? = data?.getData();
                Log.d("path",uri?.path.toString())
                filepathvalue = uri?.path.toString();
                Toast.makeText(context, uri?.path,Toast.LENGTH_SHORT).show();
                // permissions
                val wp  = ActivityCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                val rp  = ActivityCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.READ_EXTERNAL_STORAGE);
                val mp  = ActivityCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.MANAGE_EXTERNAL_STORAGE);

                if(wp != PackageManager.PERMISSION_GRANTED || rp!= PackageManager.PERMISSION_GRANTED || mp!=PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                    );

                    Log.d("granted","permission")
                }


                //read data
//                var reader: BufferedReader? = null
//                var s: FileInputStream? = null
//                var f : File;
//                try {
//
//                 //   /storage/emulated/0/Download/19908___Introduction to Algorithms.pdf
//                    f= File("/storage/emulated/0/Download/Data");
//                    s = FileInputStream(f)
//                    reader = BufferedReader(InputStreamReader(s))
//                    var line: String = reader.readLine()
//                    Log.d("dataheading", line)
//                    while (line != null) {
//                        Log.d("dataretrieved", line)
//                        line = reader.readLine()
//                    }
//                } catch (e: FileNotFoundException) {
//                    e.printStackTrace()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }




            }

        }


    }

    public fun openfilechooser()
    {
        var intent : Intent  = Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent,requestcode);

    }



}