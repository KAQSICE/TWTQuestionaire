package com.tranced.twtquestionaire.questionaire.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.tranced.twtquestionaire.Question
import com.tranced.twtquestionaire.R
import com.tranced.twtquestionaire.questionaire.editor.addMultipleChoiceOption

class MultipleChoiceItem(val question: Question) : Item {
    companion object MultipleChoiceItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as MultipleChoiceItemViewHolder
            item as MultipleChoiceItem
            holder.apply {
                stemTv.text = item.question.stem
                optionListRv.layoutManager = LinearLayoutManager(itemView.context)
                optionListRv.withItems {
                    for (option in item.question.options) {
                        addMultipleChoiceOption(option.content)
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.multiple_choice_item, parent, false)
            val stemTv = itemView.findViewById<TextView>(R.id.multiple_choice_text)
            val optionListRv = itemView.findViewById<RecyclerView>(R.id.multiple_choice_option_list)
            return MultipleChoiceItemViewHolder(itemView, stemTv, optionListRv)
        }
    }

    override val controller: ItemController
        get() = MultipleChoiceItemController

    private class MultipleChoiceItemViewHolder(
        itemView: View,
        val stemTv: TextView,
        val optionListRv: RecyclerView
    ) :
        RecyclerView.ViewHolder(itemView)
}

fun MutableList<Item>.addMultipleChoiceItem(question: Question) = add(MultipleChoiceItem(question))