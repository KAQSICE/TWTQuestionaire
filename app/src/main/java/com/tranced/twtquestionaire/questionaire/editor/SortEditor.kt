package com.tranced.twtquestionaire.questionaire.editor

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.tranced.twtquestionaire.GlobalPreference
import com.tranced.twtquestionaire.Option
import com.tranced.twtquestionaire.Question
import com.tranced.twtquestionaire.R
import es.dmoral.toasty.Toasty
import org.jetbrains.anko.sdk27.coroutines.onClick

private val optionList = mutableListOf<Option>()

class SortEditor : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var stemEditText: EditText
    private lateinit var compulsionSwitch: Switch
    private lateinit var answerButton: TextView
    private lateinit var scoreButton: TextView
    private lateinit var conditionButton: TextView
    private lateinit var jumpButton: TextView
    private lateinit var createButton: Button
    private var answerId = mutableListOf<Int>()
    private var score: Int = 0
    private lateinit var optionListRecyclerView: RecyclerView
    private lateinit var addOptionButton: Button
    private lateinit var sortQuestion: Question

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.q1_e_sort)
//        setActivitySize()
        findViews()
        setToolbar()
        initOptionListRecyclerView()
        setOnClickListeners()
    }

    /**
     * 这个还用说吗
     */
    private fun findViews() {
        toolbar = findViewById(R.id.common_toolbar)
        optionListRecyclerView = findViewById(R.id.q1_e_sort_option_list)
        addOptionButton = findViewById(R.id.q1_e_sort_add_option)
        toolbarTitle = findViewById(R.id.common_toolbar_title)
        stemEditText = findViewById(R.id.q1_e_sort_input_question)
        addOptionButton = findViewById(R.id.q1_e_sort_add_option)
        compulsionSwitch = findViewById(R.id.q1_e_sort_compulsion_switch)
        answerButton = findViewById(R.id.q1_e_sort_answer_button)
        scoreButton = findViewById(R.id.q1_e_sort_score_button)
        conditionButton = findViewById(R.id.q1_e_sort_condition_button)
        jumpButton = findViewById(R.id.q1_e_sort_jump_button)
        createButton = findViewById(R.id.q1_e_sort_create)
    }

    /**
     * 设置Toolbar及其属性
     */
    private fun setToolbar() {
        toolbar.title = ""
        toolbarTitle.text = "编辑题目"
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.apply {
            setNavigationOnClickListener {
                //TODO:弹个窗问候一下（你关你emoji呢
                optionList.clear()
                finish()
            }
        }
    }

    /**
     * 初始化选项列表
     */
    private fun initOptionListRecyclerView() {
        repeat(3) {
            optionList.add(Option("", it, ""))
        }
        optionListRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@SortEditor)
            adapter = SortOptionAdapter()
            (adapter as SortOptionAdapter).apply {
                (adapter as SortOptionAdapter).setOnDeleteListener(object :
                    SortOptionAdapter.OnDeleteListener {
                    override fun onDelete(position: Int) {
                        (adapter as SortOptionAdapter).apply {
                            if (optionList.size <= 1) {
                                Toasty.error(
                                    this@SortEditor,
                                    "别删啦，再删人就傻啦！",
                                    Toasty.LENGTH_SHORT
                                ).show()
                            } else {
                                delete(position)
                                optionListRecyclerView.adapter = this
                                isDeleting = false
                            }
                        }
                    }
                })
            }
        }
        addOptionButton.onClick {
            (optionListRecyclerView.adapter as SortOptionAdapter).apply {
                optionList.add(
                    Option(
                        "",
                        optionList.size,
                        ""
                    )
                )
            }
        }
    }

    /**
     * 设置按钮们的点击监听
     */
    private fun setOnClickListeners() {
        answerButton.onClick {
            var isAnyOptionEmpty = false
            for (option in optionList) {
                if (option.content.isEmpty()) isAnyOptionEmpty = true
            }
            if (!isAnyOptionEmpty) {
                val dialog = QMUIDialog.MultiCheckableDialogBuilder(this@SortEditor)
                val itemTextArray = mutableListOf<String>("不设置答案")
                for (i in 1..optionList.size) {
                    itemTextArray.add("选项${i}")
                }
                dialog.apply {
                    addItems(itemTextArray.toTypedArray(), null)
                    addAction("确定", null)
                    addAction(
                        "取消"
                    ) { _, _ -> TODO("Not yet implemented") }
                    show()
                }
            }
        }
        scoreButton.onClick {
            val builder = QMUIDialog.EditTextDialogBuilder(this@SortEditor)
            builder.setTitle("请输入分值")
                .setInputType(InputType.TYPE_CLASS_NUMBER)
                .addAction("取消") { dialog, _ ->
                    dialog.dismiss()
                }
                .addAction("确定") { dialog, _ ->
                    if (builder.editText.text.toString().isNotEmpty()) {
                        dialog.dismiss()
                        score = builder.editText.text.toString().toInt()
                        if (score > 0) {
                            scoreButton.setText(R.string.q1_e_settled)
                        } else {
                            scoreButton.setText(R.string.q1_e_unsettled)
                        }
                    } else {
                        Toasty.error(this@SortEditor, "请输入分值").show()
                        scoreButton.setText(R.string.q1_e_unsettled)
                    }
                }
                .show()
            builder.editText.setText(score.toString())
        }
        createButton.onClick {
            returnSortQuestion()
            setResult(102)
            optionList.clear()
            finish()
        }
    }

    /**
     * 获取题目能否返回的状态
     */
    private fun getIsReturnableState(): Boolean {
        if (stemEditText.text.isEmpty()) {
            return false
        }
        for (option in optionList) {
            if (option.content.isEmpty()) {
                return false
            }
        }
        return true
    }

    /**
     * 按顺序设置选项id
     */
    private fun setOptionId() {
        for (i in 0 until optionList.size) {
            optionList[i].id = i
        }
    }

    /**
     * 这个用来进行二进制转换
     */
    private fun getCorrectAnswer() {
        TODO()
    }

    /**
     * 返回一个排序题
     */
    private fun returnSortQuestion() {
        setOptionId()
        sortQuestion = Question(
            stemEditText.text.toString(),
            "排序",
            score,
            "",
            optionList.size,
            optionList
        )
        if (getIsReturnableState()) {
            //TODO:还是写进缓存比较靠谱
            GlobalPreference.q1Question = sortQuestion
        } else {
            val message: String = if (sortQuestion.stem.isEmpty()) {
                "题干不能为空"
            } else {
                "选项不能为空"
            }
            AlertDialog.Builder(this)
                .setTitle("警告")
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show()
        }
    }

    /**
     * SortOptionAdapter
     * @author TranceD
     */
    private class SortOptionAdapter :
        RecyclerView.Adapter<SortOptionViewHolder>() {
        private lateinit var onDeleteListener: OnDeleteListener
        var isDeleting = false

        /**
         * 选项删除监听器
         */
        interface OnDeleteListener {
            fun onDelete(position: Int)
        }

        fun setOnDeleteListener(onDeleteListener: OnDeleteListener) {
            this.onDeleteListener = onDeleteListener
        }

        fun delete(position: Int) {
            optionList.removeAt(position)
            notifyItemRemoved(position)
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): SortOptionViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.q1_e_single_option, parent, false)
            val editText = itemView.findViewById<EditText>(R.id.q1_e_single_option_edit_text)
            val deleteButton = itemView.findViewById<ImageView>(R.id.q1_e_single_delete)
            return SortOptionViewHolder(itemView, editText, deleteButton)
        }

        override fun getItemCount(): Int {
            return optionList.size
        }

        override fun onBindViewHolder(holder: SortOptionViewHolder, position: Int) {
            val watcher = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (!isDeleting) {
                        optionList[position].content = s.toString()
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            }
            holder.apply {
                editText.apply {
                    if (tag is TextWatcher) {
                        removeTextChangedListener(tag as TextWatcher)
                    }
                    addTextChangedListener(watcher)
                    setText(
                        optionList[position].content.toCharArray(),
                        0,
                        optionList[position].content.length
                    )
                    deleteButton.onClick {
                        onDeleteListener.onDelete(position)
                    }
                }
            }
        }
    }

    private class SortOptionViewHolder(
        itemView: View,
        val editText: EditText,
        val deleteButton: ImageView
    ) : RecyclerView.ViewHolder(itemView)
}
