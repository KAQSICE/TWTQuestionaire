package com.tranced.twtquestionaire.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.tranced.twtquestionaire.R
import com.tranced.twtquestionaire.data.Question

class BlankItem(val question: Question) : Item {
    companion object BlankItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as BlankItemViewHolder
            item as BlankItem
            holder.textView.text = item.question.stem
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.blank_item, parent, false)
            val textView = itemView.findViewById<TextView>(R.id.blank_text)
            return BlankItemViewHolder(itemView, textView)
        }
    }

    override val controller: ItemController
        get() = BlankItemController

    private class BlankItemViewHolder(itemVIew: View, val textView: TextView) :
        RecyclerView.ViewHolder(itemVIew)
}

fun MutableList<Item>.addBlankItem(question: Question) = add(BlankItem(question))