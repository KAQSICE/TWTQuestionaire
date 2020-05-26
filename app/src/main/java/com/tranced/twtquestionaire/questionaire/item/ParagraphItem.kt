package com.tranced.twtquestionaire.questionaire.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.tranced.twtquestionaire.Question
import com.tranced.twtquestionaire.R

class ParagraphItem(val question: Question) : Item {
    companion object ParagraphItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ParagraphItemViewHolder
            item as ParagraphItem
            holder.apply {
                textView.text = item.question.stem
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.paragraph_item, parent, false)
            val textView = itemView.findViewById<TextView>(R.id.paragraph_text)
            return ParagraphItemViewHolder(itemView, textView)
        }
    }

    override val controller: ItemController
        get() = ParagraphItemController

    private class ParagraphItemViewHolder(itemView: View, val textView: TextView) :
        RecyclerView.ViewHolder(itemView)
}

fun MutableList<Item>.addParagraphItem(question: Question) = add(ParagraphItem(question))