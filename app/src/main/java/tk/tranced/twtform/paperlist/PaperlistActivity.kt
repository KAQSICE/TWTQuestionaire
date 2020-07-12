package tk.tranced.twtform.paperlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import org.jetbrains.anko.sdk27.coroutines.onClick
import tk.tranced.twtform.R
import tk.tranced.twtform.data.Paper
import tk.tranced.twtform.editor.PreviewActivity
import tk.tranced.twtform.preference.GlobalPreference

class PaperlistActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.paperlist_activity)
        findViews()
        setToolbar()
        initPaperlistRecyclerView(GlobalPreference.createdPaperList)
    }

    private fun findViews() {
        toolbar = findViewById(R.id.commonToolbar)
        toolbarTitle = findViewById(R.id.commonToolbarTitle)
        recyclerView = findViewById(R.id.paperlistRecyclerView)
    }

    private fun setToolbar() {
        toolbar.title = ""
        //TODO:这里以后要分情况
        toolbarTitle.text = "我创建的"
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initPaperlistRecyclerView(paperlist: MutableList<Paper>) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@PaperlistActivity)
            withItems {
                addHeaderItem()
                for (paper in paperlist) {
                    addPaperlistItem(paper)
                }
            }
        }
    }
}

/**
 * PaperlistHeaderItem
 * 这里是问卷列表开头的仨按钮
 */
class PaperlistHeaderItem : Item {
    companion object PaperlistHeaderItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as PaperlistHeaderItemViewHolder
            item as PaperlistHeaderItem
            holder.apply {
                questionnaire.onClick {
                    TODO("这里点击应该是筛选功能")
                }
                quiz.onClick {
                    TODO("这里点击应该是筛选功能")
                }
                vote.onClick {
                    TODO("这里点击应该是筛选功能")
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.paperlist_header_item, parent, false)
            val questionnaire: ImageView = itemView.findViewById(R.id.paperlistHeaderQuestionnaire)
            val questionnaireLabel: TextView =
                itemView.findViewById(R.id.paperlistHeaderQuestionnaireLabel)
            val quiz: ImageView = itemView.findViewById(R.id.paperlistHeaderQuiz)
            val quizLabel: TextView = itemView.findViewById(R.id.paperlistHeaderQuizLabel)
            val vote: ImageView = itemView.findViewById(R.id.paperlistHeaderVote)
            val voteLabel: TextView = itemView.findViewById(R.id.paperlistHeaderVoteLabel)
            return PaperlistHeaderItemViewHolder(
                itemView,
                questionnaire,
                questionnaireLabel,
                quiz,
                quizLabel,
                vote,
                voteLabel
            )
        }
    }

    override val controller: ItemController
        get() = PaperlistHeaderItemController

    private class PaperlistHeaderItemViewHolder(
        itemView: View,
        val questionnaire: ImageView,
        val questionnaireLabel: TextView,
        val quiz: ImageView,
        val quizLabel: TextView,
        val vote: ImageView,
        val voteLabel: TextView
    ) : RecyclerView.ViewHolder(itemView)
}

//TODO:Work in progress
//TODO:当前只适用于我创建的问卷页面，这里应该考虑其它页面的兼容性
class PaperlistItem(val paper: Paper) : Item {
    companion object PaperlistItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as PaperlistItemViewHolder
            item as PaperlistItem
            holder.apply {
                titleLabel.text = item.paper.name
                //TODO:这里根据问卷是否已经提交决定文字，已经提交的就显示已提交
                stateImage.setImageResource(R.mipmap.red)
                stateLabel.text = "未发布"
                countLabel.text = "答卷数量"
                cardView.onClick {
                    val index = GlobalPreference.createdPaperList.indexOf(item.paper)
                    val mIntent = Intent(itemView.context, PreviewActivity::class.java)
                    mIntent.putExtra("parent", 1)
                    itemView.context.startActivity(mIntent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.paperlist_item, parent, false)
            val cardView: CardView = itemView.findViewById(R.id.paperlistItemCardView)
            val titleLabel: TextView = itemView.findViewById(R.id.paperlistTitleLabel)
            val stateLabel: TextView = itemView.findViewById(R.id.paperlistStateLabel)
            val stateImage: ImageView = itemView.findViewById(R.id.paperlistStateImage)
            val count: TextView = itemView.findViewById(R.id.paperlistCount)
            val countLabel: TextView = itemView.findViewById(R.id.paperlistCountLabel)
            return PaperlistItemViewHolder(
                itemView,
                cardView,
                titleLabel,
                stateLabel,
                stateImage,
                count,
                countLabel
            )
        }
    }

    override val controller: ItemController
        get() = PaperlistItemController

    private class PaperlistItemViewHolder(
        itemView: View,
        val cardView: CardView,
        val titleLabel: TextView,
        val stateLabel: TextView,
        val stateImage: ImageView,
        val count: TextView,
        val countLabel: TextView
    ) : RecyclerView.ViewHolder(itemView)
}

private fun MutableList<Item>.addHeaderItem() = add(PaperlistHeaderItem())

private fun MutableList<Item>.addPaperlistItem(paper: Paper) = add(PaperlistItem(paper))