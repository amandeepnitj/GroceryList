package com.example.grocerylist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavouriteListAdapter : RecyclerView.Adapter<FavouriteListAdapter.FavouriteListviewholder>(){


    private var list : ArrayList<ListModel> = ArrayList();



    fun addItems(items : ArrayList<ListModel>)
    {
        this.list = items;
    }






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavouriteListviewholder(
        LayoutInflater.from(parent.context).inflate(R.layout.listviewitem,parent,false)
    )

    override fun onBindViewHolder(holder: FavouriteListviewholder, position: Int) {
        val item = list[position];
        holder.bindView(item);

    }

    override fun getItemCount(): Int {
        return list.size
    }


    class FavouriteListviewholder(var view : View) : RecyclerView.ViewHolder(view)
    {


        private var itemname = view.findViewById<TextView>(R.id.itemname);
        private var quantity =  view.findViewById<TextView>(R.id.quantity);
        private var cost = view.findViewById<TextView>(R.id.cost);
        var likebutton : ImageView = view.findViewById(R.id.likebutton);


        fun bindView(listModel: ListModel)
        {

            itemname.text = listModel.itemname;
            quantity.text = listModel.quantity.toString();
            cost.text = (listModel.cost * listModel.quantity).toString();
            likebutton.setImageResource(R.drawable.like)



        }


    }

}