package com.nasaImages.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nasaImages.R
import com.nasaImages.model.Item

class ResultsAdapter(private val itemList: List<Item>,
                     private val context: Context): RecyclerView.Adapter<ResultsAdapter.RecyclerViewHolder>() {

    class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val titleView: TextView = itemView.findViewById(R.id.title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_results_list_item, parent, false)
        return RecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentItem = itemList[position]

        Glide.with(context).load(currentItem.links?.get(0)?.href).into(holder.imageView)
        holder.titleView.text = currentItem.data?.get(0)?.title
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}