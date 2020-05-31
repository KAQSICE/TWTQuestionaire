package com.tranced.twtquestionaire.trash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.tranced.twtquestionaire.R
import com.tranced.twtquestionaire.data.GlobalPreference
import com.tranced.twtquestionaire.data.Paper
import es.dmoral.toasty.Toasty
import org.jetbrains.anko.sdk27.coroutines.onClick

class TrashActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var searchSv: SearchView
    private lateinit var questionaireBtn: ImageView
    private lateinit var voteBtn: ImageView
    private lateinit var quizBtn: ImageView
    private lateinit var paperListRv: RecyclerView
    private val q1List: MutableList<Paper> = mutableListOf()
    private val vList: MutableList<Paper> = mutableListOf()
    private val q2List: MutableList<Paper> = mutableListOf()
    private val paperList: MutableList<Paper> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trash_activity)
        findViews()
        setToolbar()
        getPapers()
        initPaperFilter()
        initPaperListRv(paperList)
        setOnClickListeners()
    }

    private fun findViews() {
        toolbar = findViewById(R.id.common_toolbar)
        toolbarTitle = findViewById(R.id.common_toolbar_title)
        searchSv = findViewById(R.id.trash_search)
        questionaireBtn = findViewById(R.id.trash_q1_button)
        voteBtn = findViewById(R.id.trash_v_button)
        quizBtn = findViewById(R.id.trash_q2_button)
        paperListRv = findViewById(R.id.trash_paper_list)
    }

    private fun setToolbar() {
        toolbar.title = ""
        toolbarTitle.text = "回收站"
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun getPapers() {
        paperList.clear()
        paperList.addAll(GlobalPreference.trashCan)
    }

    private fun initPaperFilter() {
        for (paper in paperList) {
            when (paper.type) {
                "问卷" -> q1List.add(paper)
                "投票" -> vList.add(paper)
                "答题" -> q2List.add(paper)
            }
        }
    }

    private fun initPaperListRv(list: MutableList<Paper>) {
        paperListRv.apply {
            layoutManager = LinearLayoutManager(this@TrashActivity)
            withItems {
                for (paper in list) {
                    val onRestoreListener = View.OnClickListener {
                        QMUIDialog.MessageDialogBuilder(this@TrashActivity)
                            .setMessage("要恢复吗")
                            .addAction("不要") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .addAction("是的") { dialog, _ ->
                                GlobalPreference.createdPapers.add(paper)
                                GlobalPreference.trashCan.remove(paper)
                                dialog.dismiss()
                                getPapers()
                                initPaperListRv(paperList)
                            }
                            .show()
                    }
                    addPaperItem(paper, onRestoreListener)
                }
            }
        }
    }

    private fun setOnClickListeners() {
        questionaireBtn.onClick {
            initPaperListRv(q1List)
        }
        voteBtn.onClick {
            initPaperListRv(vList)
        }
        quizBtn.onClick {
            initPaperListRv(q2List)
        }
        searchSv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toasty.info(this@TrashActivity, "Work In Progress", Toasty.LENGTH_LONG).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}

private class PaperItem(
    val paper: Paper,
    val onRestoreListener: View.OnClickListener
) : Item {
    companion object PaperItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as PaperItemViewHolder
            item as PaperItem
            holder.apply {
                title.text = item.paper.title
                state.text = "N/A"
                count.text = "N/A"
                card.setOnClickListener(item.onRestoreListener)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.star_paper_item, parent, false)
            val card: CardView = itemView.findViewById(R.id.star_paper_item_card)
            val title: TextView = itemView.findViewById(R.id.star_paper_item_title)
            val state: TextView = itemView.findViewById(R.id.star_paper_item_state)
            val stateIcon: ImageView = itemView.findViewById(R.id.star_paper_item_state_icon)
            val count: TextView = itemView.findViewById(R.id.star_paper_item_count)
            return PaperItemViewHolder(
                itemView,
                card,
                title,
                state,
                stateIcon,
                count
            )
        }
    }

    override val controller: ItemController
        get() = PaperItemController

    private class PaperItemViewHolder(
        itemView: View,
        val card: CardView,
        val title: TextView,
        val state: TextView,
        val stateIcon: ImageView,
        val count: TextView
    ) : RecyclerView.ViewHolder(itemView)
}

private fun MutableList<Item>.addPaperItem(
    paper: Paper,
    onRestoreListener: View.OnClickListener
) =
    add(PaperItem(paper, onRestoreListener))