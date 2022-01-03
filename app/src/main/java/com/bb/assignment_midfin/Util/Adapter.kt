package com.bb.assignment_midfin.Util

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bb.assignment_midfin.R

class Adapter:ListAdapter<Item,Adapter.CustomViewHolder>(DiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var e=getItem(position)
        holder.name.text=e.name
        holder.location.text="Location - "+e.location
        holder.cost.text="Cost - ₹"+e.cost
        holder.preview.setImageBitmap(e.image)
    }

//    override fun getItemCount(): Int {
//        return list.size
//    }

    class CustomViewHolder(view: View):RecyclerView.ViewHolder(view){
        var name=view.findViewById<TextView>(R.id.name)
        var location=view.findViewById<TextView>(R.id.location)
        var cost=view.findViewById<TextView>(R.id.cost)
        var preview=view.findViewById<ImageView>(R.id.preview)
    }

    class DiffUtil:androidx.recyclerview.widget.DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem==newItem
        }
    }
}