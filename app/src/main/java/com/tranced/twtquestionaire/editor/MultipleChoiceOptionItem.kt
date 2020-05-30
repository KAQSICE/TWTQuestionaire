package com.tranced.twtquestionaire.editor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.tranced.twtquestionaire.R

class MultipleChoiceOptionItem(val content: String) : Item {
    companion object MultipleChoiceOptionItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as MultipleChoiceOptionItemViewHolder
            item as MultipleChoiceOptionItem
            holder.apply {
                contentTv.text = item.content
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.multiple_choice_option_item, parent, false)
            val contentTv = itemView.findViewById<TextView>(R.id.multiple_choice_option_text)
            return MultipleChoiceOptionItemViewHolder(itemView, contentTv)
        }
    }

    override val controller: ItemController
        get() = MultipleChoiceOptionItemController

    private class MultipleChoiceOptionItemViewHolder(itemView: View, val contentTv: TextView) :
        RecyclerView.ViewHolder(itemView)
}

fun MutableList<Item>.addMultipleChoiceOption(content: String) =
    add(MultipleChoiceOptionItem(content))