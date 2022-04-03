package com.example.grocerylist

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListviewAdapter : RecyclerView.Adapter<ListviewAdapter.Listviewholder>() {
    private var list : ArrayList<ListModel> = ArrayList();


    fun addItems(items : ArrayList<ListModel>)
    {
        this.list = items;
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Listviewholder(
        LayoutInflater.from(parent.context).inflate(R.layout.listviewitem,parent,false)
    )

    override fun onBindViewHolder(holder: Listviewholder, position: Int) {
            val item = list[position];
        holder.bindView(item);
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class Listviewholder(var view : View) :RecyclerView.ViewHolder(view)
    {

        private var id = 0;
        private var itemname = view.findViewById<TextView>(R.id.itemname);
        private var quantity =  view.findViewById<TextView>(R.id.quantity);
        private var cost = view.findViewById<TextView>(R.id.cost);


        fun bindView(listModel: ListModel)
        {
            itemname.text = listModel.itemname;
            quantity.text = listModel.quantity.toString();
            cost.text = listModel.cost.toString();

        }


    }

}