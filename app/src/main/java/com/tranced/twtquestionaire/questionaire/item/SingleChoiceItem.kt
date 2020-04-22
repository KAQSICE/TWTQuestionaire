package com.tranced.twtquestionaire.questionaire.item

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.tranced.twtquestionaire.questionaire.QuestionaireItem

class SingleChoiceItem : QuestionaireItem(), Item {
    companion object SingleChoiceItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as SingleChoiceItemViewHolder
            item as SingleChoiceItem
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            TODO("Not yet implemented")
        }
    }

    override val controller: ItemController
        get() = SingleChoiceItemController

    private class SingleChoiceItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}