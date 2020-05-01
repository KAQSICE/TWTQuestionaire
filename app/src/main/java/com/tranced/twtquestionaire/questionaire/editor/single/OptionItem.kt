package com.tranced.twtquestionaire.questionaire.editor.single

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.tranced.twtquestionaire.R

class OptionItem : Item {
    companion object OptionItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as OptionItemViewHolder
            item as OptionItem
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.q1_e_single_option, parent, false)
            return OptionItemViewHolder(itemView)
        }
    }

    override val controller: ItemController
        get() = OptionItemController

    private class OptionItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

fun MutableList<Item>.addSingleOption() = add(OptionItem())