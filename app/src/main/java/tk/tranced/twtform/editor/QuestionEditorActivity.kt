package tk.tranced.twtform.editor

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.orhanobut.hawk.Hawk
import es.dmoral.toasty.Toasty
import org.jetbrains.anko.sdk27.coroutines.onClick
import tk.tranced.twtform.R
import tk.tranced.twtform.data.Question
import tk.tranced.twtform.preference.GlobalPreference

var mContent: String = ""
val mOptions: MutableList<String> = mutableListOf()
var mNecessary: Boolean = false

class QuestionEditorActivity : AppCompatActivity() {
    companion object QuestionEditorActivityObject {
        lateinit var questionEditorActivity: QuestionEditorActivity
    }

    private var questionType: Int = 0
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_editor_activity)
        questionEditorActivity = this
        questionType = intent.getIntExtra("questionType", 0)
        findViews()
        setToolbar()
        initQuestionEditorRecyclerView()
        setSubmitListener()
    }

    private fun findViews() {
        toolbar = findViewById(R.id.commonToolbar)
        toolbarTitle = findViewById(R.id.commonToolbarTitle)
        recyclerView = findViewById(R.id.questionEditorRecyclerView)
        submitButton = findViewById(R.id.commonSubmitButton)
    }

    private fun setToolbar() {
        toolbar.title = ""
        toolbarTitle.text = "编辑题目"
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    fun initQuestionEditorRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.withItems {
            if (mOptions.size == 0) {
                repeat(3) {
                    mOptions.add("")
                }
            }
            addLabelItem("题面")
            addContentItem(mContent, true)
            when (questionType) {
                2 -> {

                }
                else -> {
                    for (i in 0 until mOptions.size) {
                        addOptionItem(mOptions[i], i)
                    }
                    addAddItem()
                }
            }
            addLabelItem("更多设置")
            addSwitchItem("是否必答")
            addSettingItem("设置答案", "未设置")
        }
    }

    private fun setSubmitListener() {
        submitButton.apply {
            text = "创建"
            onClick {
                if (mContent == "") {
                    Toasty.error(this@QuestionEditorActivity, "题干不能为空").show()
                } else {
                    var submitState: Boolean = true
                    val mOptionsSet: MutableSet<String> = mutableSetOf()
                    when (questionType) {
                        2 -> {

                        }
                        else -> {
                            for (option in mOptions) {
                                submitState = option.isNotEmpty()
                            }
                            mOptionsSet.addAll(mOptions)
                            submitState = mOptionsSet.size == mOptions.size
                        }
                    }
                    if (submitState) {
                        val tempPaper = GlobalPreference.paper
                        tempPaper!!.questions.add(
                            Question(
                                content = mContent,
                                type = questionType,
                                point = -1, //TODO:这里还没有做分值设定
                                correctAnswer = mutableListOf(),    //TODO:这里还没有做正确答案设置
                                options = mOptions,
                                random = 0, //TODO:这里还没有做随机题目
                                necessary = 0   //TODO:这里还没有做是否必答(其实可以做，但是我懒
                            )
                        )
                        Hawk.put("paper", tempPaper);
                        mContent = ""
                        mOptions.clear()
                        PreviewActivity.previewActivity.initPreviewRecyclerView()
                        finish()
                    } else {
                        if (mOptionsSet.size != mOptions.size) {
                            Toasty.error(this@QuestionEditorActivity, "选项重复").show()
                        } else {
                            Toasty.error(this@QuestionEditorActivity, "选项不能为空").show()
                        }
                    }
                }
            }
        }
    }
}

class QuestionEditorLabelItem(val content: String) : Item {
    companion object QuestionEditorLabelItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as QuestionEditorLabelItemViewHolder
            item as QuestionEditorLabelItem
            holder.apply {
                label.text = item.content
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.question_editor_label_item, parent, false)
            val label: TextView = itemView.findViewById(R.id.questionEditorLabel)
            return QuestionEditorLabelItemViewHolder(itemView, label)
        }
    }

    override val controller: ItemController
        get() = QuestionEditorLabelItemController

    private class QuestionEditorLabelItemViewHolder(
        itemView: View,
        val label: TextView
    ) : RecyclerView.ViewHolder(itemView)
}

class QuestionEditorContentItem(val content: String, val star: Boolean) : Item {
    companion object QuestionEditorContentItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as QuestionEditorContentItemViewHolder
            item as QuestionEditorContentItem
            holder.apply {
                val mWatcher: TextWatcher = object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {
                        mContent = p0.toString()
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }
                }
                editText.addTextChangedListener(mWatcher)
                editText.setText(item.content)
                star.apply {
                    visibility = when (item.star) {
                        true -> View.VISIBLE
                        false -> View.INVISIBLE
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.question_editor_content_item, parent, false)
            val star: TextView = itemView.findViewById(R.id.questionEditorContentStar)
            val editText: EditText = itemView.findViewById(R.id.questionEditorContentEditText)
            return QuestionEditorContentItemViewHolder(itemView, star, editText)
        }
    }

    override val controller: ItemController
        get() = QuestionEditorContentItemController

    private class QuestionEditorContentItemViewHolder(
        itemView: View,
        val star: TextView,
        val editText: EditText
    ) : RecyclerView.ViewHolder(itemView)
}

class QuestionEditorOptionItem(val content: String, val pos: Int) : Item {
    companion object QuestionEditorOptionItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as QuestionEditorOptionItemViewHolder
            item as QuestionEditorOptionItem
            holder.apply {
                val mWatcher: TextWatcher = object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {
                        mOptions[item.pos] = p0.toString()
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }
                }
                editText.addTextChangedListener(mWatcher)
                editText.setText(item.content)
                deleteImageView.onClick {
                    when (mOptions.size) {
                        1 -> {
                            Toasty.error(itemView.context, "别删啦，再删人就傻啦").show()
                        }
                        else -> {
                            mOptions.removeAt(item.pos)
                            QuestionEditorActivity.questionEditorActivity.initQuestionEditorRecyclerView()
                        }
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.question_editor_option_item, parent, false)
            val editText: EditText = itemView.findViewById(R.id.questionEditorOptionEditText)
            val deleteImageView: ImageView =
                itemView.findViewById(R.id.questionEditorOptionDeleteImageView)
            return QuestionEditorOptionItemViewHolder(itemView, editText, deleteImageView)
        }
    }

    override val controller: ItemController
        get() = QuestionEditorOptionItemController

    private class QuestionEditorOptionItemViewHolder(
        itemView: View,
        val editText: EditText,
        val deleteImageView: ImageView
    ) : RecyclerView.ViewHolder(itemView)
}

class QuestionEditorAddItem : Item {
    companion object QuestionEditorAddItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as QuestionEditorAddItemViewHolder
            item as QuestionEditorAddItem
            holder.apply {
                button.onClick {
                    mOptions.add("")
                    QuestionEditorActivity.questionEditorActivity.initQuestionEditorRecyclerView()
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.question_editor_add_item, parent, false)
            val button: Button = itemView.findViewById(R.id.questionEditorAddButton)
            return QuestionEditorAddItemViewHolder(itemView, button)
        }
    }

    override val controller: ItemController
        get() = QuestionEditorAddItemController

    class QuestionEditorAddItemViewHolder(itemView: View, val button: Button) :
        RecyclerView.ViewHolder(itemView)
}

class QuestionEditorSwitchItem(val content: String) : Item {
    companion object QuestionEditorSwitchItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as QuestionEditorSwitchItemViewHolder
            item as QuestionEditorSwitchItem
            holder.apply {
                label.text = item.content
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.question_editor_switch_item, parent, false)
            val label: TextView = itemView.findViewById(R.id.questionEditorSwitchLabel)
            val switch: Switch = itemView.findViewById(R.id.questionEditorSwitch)
            return QuestionEditorSwitchItemViewHolder(itemView, label, switch)
        }
    }

    override val controller: ItemController
        get() = QuestionEditorSwitchItemController

    private class QuestionEditorSwitchItemViewHolder(
        itemView: View,
        val label: TextView,
        val switch: Switch
    ) : RecyclerView.ViewHolder(itemView)
}

class QuestionEditorSettingItem(val content: String, val state: String) : Item {
    companion object QuestionEditorSettingItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as QuestionEditorSettingItemViewHolder
            item as QuestionEditorSettingItem
            holder.apply {
                label.text = item.content
                setting.apply {
                    text = item.state
                    onClick {
                        //TODO
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.question_editor_setting_item, parent, false)
            val label: TextView = itemView.findViewById(R.id.questionEditorSettingLabel)
            val setting: TextView = itemView.findViewById(R.id.questionEditorSetting)
            return QuestionEditorSettingItemViewHolder(itemView, label, setting)
        }
    }

    override val controller: ItemController
        get() = QuestionEditorSettingItemController

    private class QuestionEditorSettingItemViewHolder(
        itemView: View,
        val label: TextView,
        val setting: TextView
    ) : RecyclerView.ViewHolder(itemView)
}

private fun MutableList<Item>.addLabelItem(content: String) = add(QuestionEditorLabelItem(content))

private fun MutableList<Item>.addContentItem(content: String, star: Boolean) =
    add(QuestionEditorContentItem(content, star))

private fun MutableList<Item>.addOptionItem(content: String, pos: Int) =
    add(QuestionEditorOptionItem(content, pos))

private fun MutableList<Item>.addAddItem() = add(QuestionEditorAddItem())

private fun MutableList<Item>.addSwitchItem(content: String) =
    add(QuestionEditorSwitchItem(content))

private fun MutableList<Item>.addSettingItem(content: String, state: String) =
    add(QuestionEditorSettingItem(content, state))