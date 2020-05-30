package com.tranced.twtquestionaire.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.tranced.twtquestionaire.R
import com.tranced.twtquestionaire.data.Question
import com.tranced.twtquestionaire.editor.addSortOptionItem

class SortItem(val question: Question) : Item {
    companion object SortItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as SortItemViewHolder
            item as SortItem
            holder.apply {
                stemTv.text = item.question.stem
                optionListRv.apply {
                    layoutManager = LinearLayoutManager(itemView.context)
                    withItems {
                        for (option in item.question.options) {
                            addSortOptionItem(content = option.content)
                        }
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.sort_item, parent, false)
            val stemTv = itemView.findViewById<TextView>(R.id.sort_text)
            val optionListRv = itemView.findViewById<RecyclerView>(R.id.sort_option_list)
            return SortItemViewHolder(itemView, stemTv, optionListRv)
        }
    }

    override val controller: ItemController
        get() = SortItemController

    private class SortItemViewHolder(
        itemView: View,
        val stemTv: TextView,
        val optionListRv: RecyclerView
    ) : RecyclerView.ViewHolder(itemView)
}

fun MutableList<Item>.addSortItem(question: Question) = add(SortItem(question))