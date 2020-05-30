package com.tranced.twtquestionaire.editor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.tranced.twtquestionaire.R

class SortOptionItem(val content: String, val number: Int) : Item {
    companion object SortOptionItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as SortOptionItemViewHolder
            item as SortOptionItem
            holder.apply {
                contentTv.text = item.content
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.sort_option_item, parent, false)
            val contentTv = itemView.findViewById<TextView>(R.id.sort_option_text)
            return SortOptionItemViewHolder(itemView, contentTv)
        }
    }

    override val controller: ItemController
        get() = SortOptionItemController

    private class SortOptionItemViewHolder(itemView: View, val contentTv: TextView) :
        RecyclerView.ViewHolder(itemView)
}

fun MutableList<Item>.addSortOptionItem(content: String) = add(SortOptionItem(content, 1))