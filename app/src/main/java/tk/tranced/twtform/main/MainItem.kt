package tk.tranced.twtform.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import org.jetbrains.anko.sdk27.coroutines.onClick
import tk.tranced.twtform.R
import tk.tranced.twtform.newpaper.NewpaperActivity

class MainItem(val pos: Int) : Item {
    companion object MainItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as MainItemViewHolder
            item as MainItem
            holder.apply {
                when (item.pos) {
                    0 -> {
                        mainItemIcon.setImageResource(R.mipmap.main_new_icon)
                        mainItemContent.text = "新建"
                        mainItemArrow.visibility = View.INVISIBLE
                        mainItemCardView.onClick {
                            QMUIBottomSheet.BottomListSheetBuilder(itemView.context)
                                .addItem("问卷")
                                .addItem("答题")
                                .addItem("投票")
                                .setOnSheetItemClickListener { dialog, _, position, _ ->
                                    dialog.dismiss()
                                    val intent =
                                        Intent(itemView.context, NewpaperActivity::class.java)
                                    intent.putExtra("paperType", position)
                                    itemView.context.startActivity(intent)
                                }
                                .build()
                                .show()
                        }
                    }
                    1 -> {
                        mainItemIcon.setImageResource(R.mipmap.main_participated_icon)
                        mainItemContent.text = "我参与的"
                    }
                    2 -> {
                        mainItemIcon.setImageResource(R.mipmap.main_created_icon)
                        mainItemContent.text = "我创建的"
                    }
                    3 -> {
                        mainItemIcon.setImageResource(R.mipmap.main_star_icon)
                        mainItemContent.text = "我收藏的"
                    }
                    4 -> {
                        mainItemIcon.setImageResource(R.mipmap.main_trash_icon)
                        mainItemContent.text = "回收站"
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.main_item, parent, false)
            val mainItemIcon: ImageView = itemView.findViewById(R.id.mainItemIcon)
            val mainItemContent: TextView = itemView.findViewById(R.id.mainItemContent)
            val mainItemArrow: ImageView = itemView.findViewById(R.id.mainItemArrow)
            val mainItemCardView: CardView = itemView.findViewById(R.id.mainItemCardView)
            return MainItemViewHolder(
                itemView,
                mainItemIcon,
                mainItemContent,
                mainItemArrow,
                mainItemCardView
            )
        }
    }

    override val controller: ItemController
        get() = MainItemController

    private class MainItemViewHolder(
        itemView: View,
        val mainItemIcon: ImageView,
        val mainItemContent: TextView,
        val mainItemArrow: ImageView,
        val mainItemCardView: CardView
    ) : RecyclerView.ViewHolder(itemView)
}

fun MutableList<Item>.addMainItem(pos: Int) = add(MainItem(pos))