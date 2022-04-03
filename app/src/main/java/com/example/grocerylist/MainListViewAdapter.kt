package com.example.grocerylist

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainListViewAdapter : RecyclerView.Adapter<MainListViewAdapter.MainListViewHolder>() {
        private var mainlist: ArrayList<String>  = ArrayList();


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewHolder = MainListViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.mainlist,parent,false)
    )

    override fun onBindViewHolder(holder: MainListViewHolder, position: Int) {
        val item = mainlist[position];
        holder.bindView(item)

    }

    override fun getItemCount(): Int {
        return mainlist.size;
    }

    fun addItems(items: ArrayList<String>)
    {
        this.mainlist = items;
    }

    class MainListViewHolder(var view : View) :RecyclerView.ViewHolder(view){
        private var listnameitem = view.findViewById<TextView>(R.id.listnameitem);

        fun bindView(value : String)
        {
            listnameitem.text = value;
        }


    }
}