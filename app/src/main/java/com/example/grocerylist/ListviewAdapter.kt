package com.example.grocerylist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListviewAdapter : RecyclerView.Adapter<ListviewAdapter.Listviewholder>() {
    private var list : ArrayList<ListModel> = ArrayList();
    private var onClickItemlistview : ((ListModel)->Unit) ? = null;


    fun addItems(items : ArrayList<ListModel>)
    {
        this.list = items;
    }

    fun setOnClickItemlistview(callback : (ListModel)-> Unit)
    {
        this.onClickItemlistview = callback;
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Listviewholder(
        LayoutInflater.from(parent.context).inflate(R.layout.listviewitem,parent,false)
    )

    override fun onBindViewHolder(holder: Listviewholder, position: Int) {
            val item = list[position];
        holder.bindView(item);
        holder.Item_listview.setOnClickListener{onClickItemlistview?.invoke(item)};
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
        private var q = view.findViewById<TextView>(R.id.q);
        private var d = view.findViewById<TextView>(R.id.d);
        var Item_listview = view.findViewById<LinearLayout>(R.id.Item_listview);
        var likebutton : ImageView = view.findViewById(R.id.likebutton);


        fun bindView(listModel: ListModel)
        {

            itemname.text = listModel.itemname;
            quantity.text = listModel.quantity.toString();
            cost.text = (listModel.cost * listModel.quantity).toString();

            if(listModel.bought == 1)
            {
//                Item_listview.setBackgroundColor(Color.GRAY)
                itemname.setTextColor(Color.parseColor("#f7511f"))
                quantity.setTextColor(Color.parseColor("#f7511f"))
                cost.setTextColor(Color.parseColor("#f7511f"))
                q.setTextColor(Color.parseColor("#f7511f"))
                d.setTextColor(Color.parseColor("#f7511f"))



            }
            else
            {
//                Item_listview.setBackgroundColor(Color.WHITE)
                itemname.setTextColor(Color.GRAY)
                quantity.setTextColor(Color.GRAY)
                cost.setTextColor(Color.GRAY)
                q.setTextColor(Color.GRAY)
                d.setTextColor(Color.GRAY)

            }
            if(listModel.favourite == 0)
            {
                likebutton.setImageResource(R.drawable.dislike)
            }
            else
            {
                likebutton.setImageResource(R.drawable.like)

            }

        }


    }

}