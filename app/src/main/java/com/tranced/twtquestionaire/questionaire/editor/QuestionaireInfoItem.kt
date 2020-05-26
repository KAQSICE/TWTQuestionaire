package com.tranced.twtquestionaire.questionaire.editor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.tranced.twtquestionaire.R

class QuestionaireInfoItem(val questionaireTitle: String, val questionaireDescription: String?) :
    Item {
    companion object QuestionaireInfoItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as QuestionaireInfoItemViewHolder
            item as QuestionaireInfoItem
            holder.title.text = item.questionaireTitle
            if (item.questionaireDescription!!.isNotEmpty()) {
                holder.description.text = item.questionaireDescription
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.q1_e_info_item, parent, false)
            val questionaireTitle =
                itemView.findViewById<TextView>(R.id.q1_e_info_title)
            val questionaireDescription =
                itemView.findViewById<TextView>(R.id.q1_e_info_description)
            return QuestionaireInfoItemViewHolder(
                itemView,
                questionaireTitle,
                questionaireDescription
            )
        }
    }

    override val controller: ItemController
        get() = QuestionaireInfoItemController

    private class QuestionaireInfoItemViewHolder(
        itemView: View,
        val title: TextView,
        val description: TextView
    ) : RecyclerView.ViewHolder(itemView)
}

fun MutableList<Item>.addInfo(title: String, description: String?) =
    add(QuestionaireInfoItem(title, description))