package com.example.grocerylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EditListViewAdapter : RecyclerView.Adapter<EditListViewAdapter.listviewHolder>(){
     var list : ArrayList<ListModel> = ArrayList();
    private var onClickDeleteButton : ((ListModel)->Unit) ? = null;



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = listviewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.edititems_listview,parent,false)
    )


    fun setOnClickDeleteButton(callback : (ListModel)-> Unit)
    {
        this.onClickDeleteButton = callback;
    }


    override fun onBindViewHolder(holder: listviewHolder, position: Int) {
        val item = list[position];
        holder.bindView(item);
        holder.deletebutton.setOnClickListener{onClickDeleteButton?.invoke(item)};
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(items : ArrayList<ListModel>)
    {
        this.list = items;
        notifyDataSetChanged()
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
        var deletebutton = view.findViewById<ImageView>(R.id.removebutton);

        fun bindView(listModel: ListModel)
        {
            itemname.text = listModel.itemname;
            quantity.text = listModel.quantity.toString();
            cost.text = listModel.cost.toString();
        }
    }

}