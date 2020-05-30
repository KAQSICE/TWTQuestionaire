package com.tranced.twtquestionaire.editor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.tranced.twtquestionaire.R

class SavePaperButton(val onCLickListener: View.OnClickListener) : Item {
    companion object SavePaperButtonController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as SavePaperButtonViewHolder
            item as SavePaperButton
            holder.apply {
                button.setOnClickListener(item.onCLickListener)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.save_paper_button, parent, false)
            val button = itemView.findViewById<Button>(R.id.q1_e_save_button)
            return SavePaperButtonViewHolder(itemView, button)
        }
    }

    override val controller: ItemController
        get() = SavePaperButtonController

    private class SavePaperButtonViewHolder(itemView: View, val button: Button) :
        RecyclerView.ViewHolder(itemView)
}

fun MutableList<Item>.addSavePaperButton(onCLickListener: View.OnClickListener) =
    add(SavePaperButton(onCLickListener))