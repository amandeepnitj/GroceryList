package com.example.grocerylist

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainListViewAdapter : RecyclerView.Adapter<MainListViewAdapter.MainListViewHolder>() {
        private var mainlist: ArrayList<String>  = ArrayList();
        private var onClickItem:((String)-> Unit)?= null
    private var onClickOptionButton: ((String)-> Unit) ?= null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewHolder = MainListViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.mainlist,parent,false)
    )

    fun setOnClickItem(callback : (String)-> Unit)
    {
        this.onClickItem = callback;

    }

    fun setOnOptionbuttonClick(callback: (String) -> Unit)
    {
        this.onClickOptionButton = callback;
    }

    override fun onBindViewHolder(holder: MainListViewHolder, position: Int) {
        val item = mainlist[position];
        holder.bindView(item)
        holder.itemView.setOnClickListener{onClickItem?.invoke(item)}
        holder.optionButton.setOnClickListener{onClickOptionButton?.invoke(item)}

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
        var optionButton : ImageView = view.findViewById(R.id.threedots);

        fun bindView(value : String)
        {
            listnameitem.text = value;
        }


    }
}