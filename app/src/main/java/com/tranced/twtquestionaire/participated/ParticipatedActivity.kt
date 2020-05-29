package com.tranced.twtquestionaire.participated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.tranced.twtquestionaire.Paper
import com.tranced.twtquestionaire.R
import com.tranced.twtquestionaire.data.PaperPreference
import org.jetbrains.anko.sdk27.coroutines.onClick

class ParticipatedActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var questionBtn: ImageButton
    private lateinit var voteBtn: ImageButton
    private lateinit var quizBtn: ImageButton
    private lateinit var paperListRv: RecyclerView
    private val q1List: MutableList<Paper> = mutableListOf()
    private val vList: MutableList<Paper> = mutableListOf()
    private val q2List: MutableList<Paper> = mutableListOf()
    private val paperList: MutableList<Paper> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.participated_activity)
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
        questionBtn = findViewById(R.id.p_q1_button)
        voteBtn = findViewById(R.id.p_v_button)
        quizBtn = findViewById(R.id.p_q2_button)
        paperListRv = findViewById(R.id.p_paper_list)
    }

    private fun setToolbar() {
        toolbar.title = ""
        toolbarTitle.text = "我参与的"
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
        paperList.addAll(PaperPreference.paperList)
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
            layoutManager = LinearLayoutManager(this@ParticipatedActivity)
            withItems {
                for (paper in list) {
                    addPaperItem(paper)
                }
            }
        }
    }

    private fun setOnClickListeners() {
        questionBtn.onClick {
            initPaperListRv(q1List)
        }
        voteBtn.onClick {
            initPaperListRv(vList)
        }
        quizBtn.onClick {
            initPaperListRv(q2List)
        }
    }
}

private class PaperItem(val title: String, val state: String, val date: String, val score: String) :
    Item {
    companion object PaperItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as PaperItemViewHolder
            item as PaperItem
            holder.apply {
                title.text = item.title
                state.text = item.state
                date.text = item.date
                score.text = item.score
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.p_paper_item, parent, false)
            val title: TextView = itemView.findViewById(R.id.p_paper_item_title)
            val state: TextView = itemView.findViewById(R.id.p_paper_item_state)
            val stateIcon: ImageView = itemView.findViewById(R.id.p_paper_item_state_icon)
            val date: TextView = itemView.findViewById(R.id.p_paper_item_date)
            val score: TextView = itemView.findViewById(R.id.p_paper_item_score)

            return PaperItemViewHolder(itemView, title, state, stateIcon, date, score)
        }
    }

    override val controller: ItemController
        get() = PaperItemController

    private class PaperItemViewHolder(
        itemView: View,
        val title: TextView,
        val state: TextView,
        val stateIcon: ImageView,
        val date: TextView,
        val score: TextView
    ) : RecyclerView.ViewHolder(itemView)
}

private fun MutableList<Item>.addPaperItem(paper: Paper) =
    add(PaperItem(paper.title, "state", "date", "分数"))