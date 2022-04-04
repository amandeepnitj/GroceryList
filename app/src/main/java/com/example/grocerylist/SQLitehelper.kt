package com.example.grocerylist

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context;
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception
import kotlin.math.cos

class SQLitehelper(context: Context) :SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "list.db";

        private const val DATABASE_VERSION = 1;
        private const val TABLE = "listtable"
        private const val ID = "id"
        private const val LIST_NAME ="listname"
        private const val ITEM_NAME = "itemname"
        private const val QUANTITY = "quantity"
        private const val COST = "cost"
        private const val BOUGHT = "bought";



    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblStatement = ("CREATE TABLE " + TABLE + "( " + ID + " INTEGER PRIMARY KEY, " + LIST_NAME + " TEXT," + ITEM_NAME + " TEXT, " + QUANTITY
                + " INTEGER, " + COST + " REAL, "+ BOUGHT + " INTEGER )");
        db?.execSQL(createTblStatement);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldversion: Int, newversion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE");
        onCreate(db);

    }
    fun insertData(list:ListModel): Long{
        val db = this.writableDatabase;
        val contentValues =ContentValues();
        contentValues.put(ID,list.id);
        contentValues.put(LIST_NAME,list.listname);
        contentValues.put(ITEM_NAME,list.itemname);
        contentValues.put(QUANTITY,list.quantity);
        contentValues.put(COST,list.cost);
        contentValues.put(BOUGHT,list.bought);

        val success = db.insert(TABLE,null,contentValues);
        db.close();
        return success;

    }


    @SuppressLint("Range")
    fun getallData(value : String) :ArrayList<ListModel>{
        val listarray: ArrayList<ListModel> = ArrayList();
        val selectquery = "SELECT * FROM " + TABLE + " where listname = ? ";
        val db = this.readableDatabase;

        val cursor : Cursor?

        try{
            cursor = db.rawQuery(selectquery, arrayOf(value));


        }
        catch(e: Exception)
        {
            e.printStackTrace();
            db.execSQL(selectquery);
            return ArrayList();

        }

        var id :Int;
        var listname : String;
        var itemname : String;
        var quanity : Int;
        var cost : Float;
        var bought : Int;

        if(cursor.moveToFirst())
        {
            do{
                id = cursor.getInt(cursor.getColumnIndex("id"));
                listname  = cursor.getString(cursor.getColumnIndex("listname"));
                itemname = cursor.getString(cursor.getColumnIndex("itemname"));
                quanity = cursor.getInt(cursor.getColumnIndex("quantity"));
                cost = cursor.getFloat(cursor.getColumnIndex("cost"));
                bought = cursor.getInt(cursor.getColumnIndex("bought"));
                val list1 = ListModel(id= id,listname =listname,itemname =  itemname, quantity =  quanity, cost = cost, bought = bought );
                listarray.add(list1);

            }
                while(cursor.moveToNext())
        }

        return listarray;



    }


    @SuppressLint("Range")
    fun getlistnameData() :ArrayList<String>{
        val mainlistarray: ArrayList<String> = ArrayList();
        val selectquery = "SELECT distinct listname  as LISTNAME FROM " + TABLE;
        val db = this.readableDatabase;

        val cursor : Cursor?

        try{
            cursor = db.rawQuery(selectquery,null);


        }
        catch(e: Exception)
        {
            e.printStackTrace();
            Log.e("pppp","data of main list names can't find");
            db.execSQL(selectquery);
            return ArrayList();

        }


        var mainlistname : String;

        if(cursor.moveToFirst())
        {
            do{

                mainlistname  = cursor.getString(cursor.getColumnIndex("LISTNAME"));
                mainlistarray.add(mainlistname);

            }
            while(cursor.moveToNext())
        }

        return mainlistarray;



    }

    fun deleteItem(id :Int): Int
    {
        val db =this.writableDatabase;
        val contentValues = ContentValues();

        contentValues.put(ID,id);

        val success = db.delete(TABLE,"id=$id",null);
        db.close();
        return success;

    }


    fun deleteWholeList(listname1 :String): Int
    {
        val db =this.writableDatabase;
        val contentValues = ContentValues();

        contentValues.put(LIST_NAME,listname1);

        val success = db.delete(TABLE, LIST_NAME+ "=?", arrayOf(listname1));
        db.close();
        return success;

    }



}