package com.example.mypagingpoc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ItemAdapter(private val context: Context) :
    PagedListAdapter<Item?, ItemAdapter.ItemViewHolder?>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.layout_rv_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Item? = getItem(position)
        item?.let {
            Glide.with(context)
                .load(item.owner?.profile_image)
                .into(holder.imageView)
            holder.textView.text = item.owner?.display_name
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.image_view)
        var textView: TextView = itemView.findViewById(R.id.text_view)
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Item?> =
            object : DiffUtil.ItemCallback<Item?>() {
                override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                    return oldItem.answer_id == newItem.answer_id
                }

                override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                    return oldItem == newItem
                }
            }
    }

}