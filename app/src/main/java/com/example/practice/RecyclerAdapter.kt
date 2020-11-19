package com.example.practice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var title = arrayOf("chapter one", "chapter two")





    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = title[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return  ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return title.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var itemTitle: TextView

        init {
            itemTitle = itemView.findViewById(R.id.item_Title)

            itemView.setOnClickListener{
                val position: Int = adapterPosition

                Toast.makeText(itemView.context, "Ты нажал ${title[position]}", Toast.LENGTH_LONG).show()
            }
        }
    }


}