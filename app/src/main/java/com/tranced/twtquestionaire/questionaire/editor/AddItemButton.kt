package com.tranced.twtquestionaire.questionaire.editor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.tranced.twtquestionaire.R

class AddItemButton(val onClickListener: View.OnClickListener) : Item {

    companion object AddItemButtonController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as AddItemButtonViewHolder
            item as AddItemButton
            holder.itemView.setOnClickListener(item.onClickListener)
            holder.button.setOnClickListener(item.onClickListener)
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.questionaire_editor_add_item_button, parent, false)
            val button = itemView.findViewById<Button>(R.id.questionaire_editor_add_item_button)
            return AddItemButtonViewHolder(itemView, button)
        }
    }

    override val controller: ItemController
        get() = AddItemButtonController

    private class AddItemButtonViewHolder(itemView: View, val button: Button) :
        RecyclerView.ViewHolder(itemView)
}

fun MutableList<Item>.addAddItemButton(onClickListener: View.OnClickListener) =
    add(AddItemButton(onClickListener))