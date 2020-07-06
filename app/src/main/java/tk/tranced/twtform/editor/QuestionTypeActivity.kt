package tk.tranced.twtform.editor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import es.dmoral.toasty.Toasty
import org.jetbrains.anko.sdk27.coroutines.onClick
import tk.tranced.twtform.R

class QuestionTypeActivity : AppCompatActivity() {
    companion object QuestionTypeActivityObject {
        lateinit var questionTypeActivity: QuestionTypeActivity
    }

    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_type_activity)
        questionTypeActivity = this
        findViews()
        setToolbar()
        initRecyclerView()
    }

    private fun findViews() {
        toolbar = findViewById(R.id.commonToolbar)
        toolbarTitle = findViewById(R.id.commonToolbarTitle)
        recyclerView = findViewById(R.id.questionTypeRecyclerView)
    }

    private fun setToolbar() {
        toolbar.title = ""
        toolbarTitle.text = "选择题型"
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.withItems {
            for (i in 0..2) {
                addQuestionTypeItem(i)
            }
            addQuestionTypeImportItem()
        }
    }
}

class QuestionTypeItem(val questionType: Int) : Item {
    companion object QuestionTypeItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as QuestionTypeItemViewHolder
            item as QuestionTypeItem
            holder.apply {
                when (item.questionType) {
                    0 -> {
                        content.text = "单选题"
                        cardView.onClick {
                            val mIntent =
                                Intent(itemView.context, QuestionEditorActivity::class.java)
                            mIntent.putExtra("questionType", 0)
                            itemView.context.startActivity(mIntent)
                            QuestionTypeActivity.questionTypeActivity.finish()

                        }
                    }
                    1 -> {
                        content.text = "多选题"
                        cardView.onClick {
                            val mIntent =
                                Intent(itemView.context, QuestionEditorActivity::class.java)
                            mIntent.putExtra("questionType", 1)
                            itemView.context.startActivity(mIntent)
                            QuestionTypeActivity.questionTypeActivity.finish()
                        }
                    }
                    2 -> {
                        content.text = "填空题"
                        cardView.onClick {
                            val mIntent =
                                Intent(itemView.context, QuestionEditorActivity::class.java)
                            mIntent.putExtra("questionType", 2)
                            itemView.context.startActivity(mIntent)
                            QuestionTypeActivity.questionTypeActivity.finish()
                        }
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.question_type_item, parent, false)
            val cardView: CardView = itemView.findViewById(R.id.questionTypeCardView)
            val content: TextView = itemView.findViewById(R.id.questionTypeContent)
            return QuestionTypeItemViewHolder(itemView, cardView, content)
        }
    }

    override val controller: ItemController
        get() = QuestionTypeItemController

    private class QuestionTypeItemViewHolder(
        itemView: View,
        val cardView: CardView,
        val content: TextView
    ) : RecyclerView.ViewHolder(itemView)
}

class QuestionTypeImportItem : Item {
    companion object QuestionTypeImportItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as QuestionTypeImportItemViewHolder
            item as QuestionTypeImportItem
            holder.apply {
                importButton.apply {
                    text = "批量导入"
                    onClick { Toasty.info(itemView.context, "Work in progress").show() }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.question_type_import_item, parent, false)
            val importButton: Button = itemView.findViewById(R.id.questionTypeImportButton)
            return QuestionTypeImportItemViewHolder(itemView, importButton)
        }
    }

    override val controller: ItemController
        get() = QuestionTypeImportItemController

    private class QuestionTypeImportItemViewHolder(
        itemView: View,
        val importButton: Button
    ) : RecyclerView.ViewHolder(itemView)
}

fun MutableList<Item>.addQuestionTypeItem(paperType: Int) = add(QuestionTypeItem(paperType))

fun MutableList<Item>.addQuestionTypeImportItem() = add(QuestionTypeImportItem())