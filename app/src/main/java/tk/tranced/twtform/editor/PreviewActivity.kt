package tk.tranced.twtform.editor

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.orhanobut.hawk.Hawk
import org.jetbrains.anko.sdk27.coroutines.onClick
import tk.tranced.twtform.R
import tk.tranced.twtform.data.Paper
import tk.tranced.twtform.preference.GlobalPreference

class PreviewActivity : AppCompatActivity() {
    companion object PreviewActivityObject {
        lateinit var previewActivity: PreviewActivity
    }

    private var paperType: Int = 0
    private val paperTypeText = arrayOf("问卷", "答题", "投票")
    private var parentIndicator: Int = 0
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preview_activity)
        previewActivity = this
        paperType = intent.getIntExtra("paperType", 0)
        parentIndicator = intent.getIntExtra("parent", 0)
        findViews()
        setToolbar()
        initPreviewRecyclerView()
    }

    private fun findViews() {
        toolbar = findViewById(R.id.commonToolbar)
        toolbarTitle = findViewById(R.id.commonToolbarTitle)
        recyclerView = findViewById(R.id.previewRecyclerView)
    }

    @SuppressLint("SetTextI18n")
    private fun setToolbar() {
        toolbar.title = ""
        toolbarTitle.text = "编辑${paperTypeText[paperType]}"
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    fun initPreviewRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.withItems {
            addPreviewInfoItem(GlobalPreference.paper!!)
            for (question in GlobalPreference.paper!!.questions) {
                when (question.type) {
                    0 -> addSingleItem(question.content, question.options)
                    1 -> addMultiItem(question.content, question.options)
                    2 -> addBlankItem(question.content)
                }
            }
            addPreviewAddItem()
            addPreviewCreateItem()
        }
    }
}

class PreviewInfoItem(val paper: Paper) : Item {
    companion object PreviewInfoItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as PreviewInfoItemViewHolder
            item as PreviewInfoItem
            holder.apply {
                title.text = item.paper.name
                description.text = when (item.paper.description) {
                    null -> "这个${
                    when (item.paper.type) {
                        1 -> "答题"
                        2 -> "投票"
                        else -> "问卷"
                    }
                    }还没有说明哟"
                    else -> item.paper.description
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.preview_info_item, parent, false)
            val title: TextView = itemView.findViewById(R.id.previewInfoTitle)
            val description: TextView = itemView.findViewById(R.id.previewInfoDescription)
            return PreviewInfoItemViewHolder(itemView, title, description)
        }
    }

    override val controller: ItemController
        get() = PreviewInfoItemController

    private class PreviewInfoItemViewHolder(
        itemView: View,
        val title: TextView,
        val description: TextView
    ) : RecyclerView.ViewHolder(itemView)
}

class PreviewAddItem : Item {
    companion object PreviewAddItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as PreviewAddItemViewHolder
            item as PreviewAddItem
            holder.apply {
                addButton.apply {
                    text = "添加题目"
                    onClick {
                        val mIntent = Intent(itemView.context, QuestionTypeActivity::class.java)
                        itemView.context.startActivity(mIntent)
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.preview_add_item, parent, false)
            val addButton: Button = itemView.findViewById(R.id.previewAddButton)
            return PreviewAddItemViewHolder(itemView, addButton)
        }
    }

    override val controller: ItemController
        get() = PreviewAddItemController

    private class PreviewAddItemViewHolder(itemView: View, val addButton: Button) :
        RecyclerView.ViewHolder(itemView)
}

class PreviewSingleItem(val content: String, val options: MutableList<String>) : Item {
    companion object PreviewSingleItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as PreviewSingleItemViewHolder
            item as PreviewSingleItem
            holder.apply {
                content.text = item.content
                for (option in item.options) {
                    val radioButton = RadioButton(itemView.context)
                    radioButton.text = option
                    radioButton.id = item.options.indexOf(option)
                    val layoutParams: RadioGroup.LayoutParams = RadioGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.setMargins(25, 7, 25, 7)
                    options.addView(radioButton, layoutParams)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.preview_single_item, parent, false)
            val content: TextView = itemView.findViewById(R.id.previewSingleContent)
            val options: RadioGroup = itemView.findViewById(R.id.previewSingleOptions)
            return PreviewSingleItemViewHolder(itemView, content, options)
        }
    }

    override val controller: ItemController
        get() = PreviewSingleItemController

    private class PreviewSingleItemViewHolder(
        itemView: View,
        val content: TextView,
        val options: RadioGroup
    ) : RecyclerView.ViewHolder(itemView)
}

class PreviewMultiItem(val content: String, val options: MutableList<String>) : Item {
    companion object PreviewMultiItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as PreviewMultiItemViewHolder
            item as PreviewMultiItem
            holder.apply {
                content.text = item.content
                options.layoutManager = LinearLayoutManager(itemView.context)
                options.withItems {
                    for (option in item.options) {
                        addOptionItem(option)
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.preview_multi_item, parent, false)
            val content: TextView = itemView.findViewById(R.id.previewMultiContent)
            val options: RecyclerView = itemView.findViewById(R.id.previewMultiOptions)
            return PreviewMultiItemViewHolder(itemView, content, options)
        }

        private fun MutableList<Item>.addOptionItem(content: String) = add(OptionItem(content))
    }

    override val controller: ItemController
        get() = PreviewMultiItemController

    private class PreviewMultiItemViewHolder(
        itemView: View,
        val content: TextView,
        val options: RecyclerView
    ) : RecyclerView.ViewHolder(itemView)

    private class OptionItem(val content: String) : Item {
        companion object OptionItemController : ItemController {
            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
                holder as OptionItemViewHolder
                item as OptionItem
                holder.apply {
                    content.text = item.content
                }
            }

            override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
                val itemView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.preview_multi_option_item, parent, false)
                val content: TextView = itemView.findViewById(R.id.previewMultiOptionContent)
                return OptionItemViewHolder(itemView, content)
            }
        }

        override val controller: ItemController
            get() = OptionItemController

        private class OptionItemViewHolder(itemView: View, val content: TextView) :
            RecyclerView.ViewHolder(itemView)
    }
}

class PreviewBlankItem(val content: String) : Item {
    companion object PreviewBlankItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as PreviewBlankItemViewHolder
            item as PreviewBlankItem
            holder.apply {
                content.text = item.content
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.preview_blank_item, parent, false)
            val content: TextView = itemView.findViewById(R.id.previewBlankContent)
            return PreviewBlankItemViewHolder(itemView, content)
        }
    }

    override val controller: ItemController
        get() = PreviewBlankItemController

    private class PreviewBlankItemViewHolder(
        itemView: View,
        val content: TextView
    ) : RecyclerView.ViewHolder(itemView)
}

class PreviewCreateItem : Item {
    companion object PreviewCreateItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as PreviewCreateItemViewHolder
            item as PreviewCreateItem
            holder.apply {
                createButton.apply {
                    text = "创建"
                    //TODO:这里应该也要判断上级
                    onClick {
                        //把问题加入到我创建的问卷之列
                        val tempCreatedPaperList = GlobalPreference.createdPaperList
                        tempCreatedPaperList.add(GlobalPreference.paper!!)
                        Hawk.put("createdPaperList", tempCreatedPaperList)
                        PreviewActivity.previewActivity.finish()
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.preview_create_item, parent, false)
            val createButton: Button = itemView.findViewById(R.id.previewCreateButton)
            return PreviewCreateItemViewHolder(itemView, createButton)
        }
    }

    override val controller: ItemController
        get() = PreviewCreateItemController

    private class PreviewCreateItemViewHolder(
        itemView: View,
        val createButton: Button
    ) : RecyclerView.ViewHolder(itemView)
}

fun MutableList<Item>.addPreviewInfoItem(paper: Paper) = add(PreviewInfoItem(paper))

fun MutableList<Item>.addPreviewAddItem() = add(PreviewAddItem())

private fun MutableList<Item>.addSingleItem(content: String, options: MutableList<String>) =
    add(PreviewSingleItem(content, options))

private fun MutableList<Item>.addMultiItem(content: String, options: MutableList<String>) =
    add(PreviewMultiItem(content, options))

private fun MutableList<Item>.addBlankItem(content: String) = add(PreviewBlankItem(content))

fun MutableList<Item>.addPreviewCreateItem() = add(PreviewCreateItem())