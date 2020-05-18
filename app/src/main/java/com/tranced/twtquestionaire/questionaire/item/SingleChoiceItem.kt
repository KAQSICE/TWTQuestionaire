package com.tranced.twtquestionaire.questionaire.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.tranced.twtquestionaire.Question
import com.tranced.twtquestionaire.R

class SingleChoiceItem(val singleChoiceQuestion: Question) : Item {
    companion object SingleChoiceItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as SingleChoiceItemViewHolder
            item as SingleChoiceItem
            holder.apply {
                textTv.text = item.singleChoiceQuestion.stem
                for (option in item.singleChoiceQuestion.options) {
                    val radioButton = RadioButton(itemView.context)
                    radioButton.text = option.content
                    radioButton.id = option.id
                    val layoutParams = RadioGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.setMargins(25, 7, 25, 7)
                    optionListRv.addView(radioButton, layoutParams)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.single_choice_item, parent, false)
            val textTv = itemView.findViewById<TextView>(R.id.single_choice_text)
            val optionListRv = itemView.findViewById<RadioGroup>(R.id.single_choice_option_list)
            return SingleChoiceItemViewHolder(itemView, textTv, optionListRv)
        }
    }

    //
    override val controller: ItemController
        get() = SingleChoiceItemController

    private class SingleChoiceItemViewHolder(
        itemView: View,
        val textTv: TextView,
        val optionListRv: RadioGroup
    ) : RecyclerView.ViewHolder(itemView)
}

fun MutableList<Item>.addSingleChoiceItem(question: Question) = add(SingleChoiceItem(question))