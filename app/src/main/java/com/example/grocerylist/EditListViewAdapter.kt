package com.example.grocerylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EditListViewAdapter : RecyclerView.Adapter<EditListViewAdapter.listviewHolder>(){
    private var list : ArrayList<ListModel> = ArrayList();




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = listviewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.edititems_listview,parent,false)
    )


    override fun onBindViewHolder(holder: listviewHolder, position: Int) {
        val item = list[position];
        holder.bindView(item);
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun additems(items: ArrayList<ListModel>)
    {
        this.list = items
    }


    class listviewHolder(var view : View) : RecyclerView.ViewHolder(view)
    {
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