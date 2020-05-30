package com.tranced.twtquestionaire.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.tranced.twtquestionaire.R
import com.tranced.twtquestionaire.data.Question

class RatingItem(val question: Question) : Item {
    companion object RatingItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as RatingItemViewHolder
            item as RatingItem
            holder.apply {
                textView.text = item.question.stem
                ratingBar.apply {
                    if (item.question.point <= 6) {
                        numStars = item.question.point
                        max = item.question.point
                        stepSize = 1f
                    } else {
                        numStars = 5
                        max = item.question.point
                        stepSize = 5f / item.question.point
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.rating_item, parent, false)
            val textView = itemView.findViewById<TextView>(R.id.rating_text)
            val ratingBar = itemView.findViewById<RatingBar>(R.id.rating_item_rating_bar)
            return RatingItemViewHolder(itemView, textView, ratingBar)
        }
    }

    override val controller: ItemController
        get() = RatingItemController

    private class RatingItemViewHolder(
        itemView: View,
        val textView: TextView,
        val ratingBar: RatingBar
    ) : RecyclerView.ViewHolder(itemView)
}

fun MutableList<Item>.addRatingItem(question: Question) = add(RatingItem(question))