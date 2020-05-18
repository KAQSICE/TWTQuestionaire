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
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.tranced.twtquestionaire.Option
import com.tranced.twtquestionaire.Question
import com.tranced.twtquestionaire.R
import com.tranced.twtquestionaire.questionaire.QuestionairePreference
import es.dmoral.toasty.Toasty
import org.jetbrains.anko.sdk27.coroutines.onClick

private val optionList = mutableListOf<Option>()

class SingleChoiceEditor : AppCompatActivity() {
    private lateinit var singleChoiceQuestion: Question
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var stemEditText: EditText
    private lateinit var optionListRecyclerView: RecyclerView
    private lateinit var addOptionButton: Button
    private lateinit var compulsionSwitch: Switch
    private lateinit var answerButton: TextView
    private lateinit var scoreButton: TextView
    private lateinit var conditionButton: TextView
    private lateinit var jumpButton: TextView
    private lateinit var createButton: Button
    private var answerId: Int = -1
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.q1_e_single_choice)
//        setActivitySize()
        findViews()
        setToolbar()
        initOptionListRecyclerView()
        setOnClickListeners()
    }

    /*
    想来想去还是全屏activity好看
    @Suppress("DEPRECATION")
    private fun setActivitySize() {
    val display = windowManager.defaultDisplay
    val layoutParams: WindowManager.LayoutParams = window.attributes
    layoutParams.apply {
    width = (display.width * 0.8).toInt()
    height = (display.height * 0.75).toInt()
    }
    }
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
                finish()
            }
            setOnMenuItemClickListener {
                if (it.itemId == R.id.q1_e_single_toolbar_create) {
                    finish()
                    //TODO:判断一下如果为空就返回null否则返回question
                }
                return@setOnMenuItemClickListener true
            }
        }
    }

    private fun findViews() {
        toolbar = findViewById(R.id.common_toolbar)
        toolbarTitle = findViewById(R.id.common_toolbar_title)
        optionListRecyclerView = findViewById(R.id.q1_e_single_option_list)
        stemEditText = findViewById(R.id.q1_e_single_input_question)
        addOptionButton = findViewById(R.id.q1_e_single_add_option)
        compulsionSwitch = findViewById(R.id.q1_e_single_compulsion_switch)
        answerButton = findViewById(R.id.q1_e_single_answer_button)
        scoreButton = findViewById(R.id.q1_e_single_score_button)
        conditionButton = findViewById(R.id.q1_e_single_condition_button)
        jumpButton = findViewById(R.id.q1_e_single_jump_button)
        createButton = findViewById(R.id.q1_e_single_create)
    }

    private fun initOptionListRecyclerView() {
        repeat(3) {
            optionList.add(Option("", it, ""))
        }
        optionListRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@SingleChoiceEditor)
            adapter = SingleOptionAdapter()
            (adapter as SingleOptionAdapter).setOnDeleteListener(object :
                SingleOptionAdapter.OnDeleteListener {
                override fun onDelete(position: Int) {
                    (adapter as SingleOptionAdapter).apply {
                        if (optionList.size <= 1) {
                            Toasty.error(
                                this@SingleChoiceEditor,
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
        addOptionButton.onClick {
            (optionListRecyclerView.adapter as SingleOptionAdapter).apply {
                optionList.add(
                    Option(
                        "",
                        optionList.size,
                        ""
                    )
                )
                notifyItemInserted(optionList.size - 1)
            }
        }
    }

    private fun setOnClickListeners() {
        answerButton.onClick {
            var isAnyOptionEmpty = false
            for (option in optionList) {
                if (option.content.isEmpty()) isAnyOptionEmpty = true
            }
            if (!isAnyOptionEmpty) {
                val bottomSheet = QMUIBottomSheet.BottomListSheetBuilder(this@SingleChoiceEditor)
                bottomSheet.apply {
                    addItem("不设置答案")
                    for (i in 1..optionList.size) {
                        addItem("选项${i}")
                    }
                    setOnSheetItemClickListener { dialog, _, position, _ ->
                        dialog.dismiss()
                        answerId = position - 1
                        if (answerId != -1) {
                            answerButton.setText(R.string.q1_e_settled)
                        } else {
                            answerButton.setText(R.string.q1_e_unsettled)
                        }
                    }
                    build().show()
                }
            } else {
                Toasty.error(this@SingleChoiceEditor, "选项不能为空").show()
            }
        }
        scoreButton.onClick {
            val builder = QMUIDialog.EditTextDialogBuilder(this@SingleChoiceEditor)
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
                        Toasty.error(this@SingleChoiceEditor, "请输入分值").show()
                        scoreButton.setText(R.string.q1_e_unsettled)
                    }
                }
                .show()
            builder.editText.setText(score.toString())
        }
        createButton.onClick {
            returnSingleChoiceQuestion()
            setResult(101)
            optionList.clear()
            finish()
        }
    }

    private fun getIsReturnableState(): Boolean {
        if (singleChoiceQuestion.stem.isEmpty()) {
            return false
        }
        for (option in singleChoiceQuestion.options) {
            if (option.content.isEmpty()) {
                return false
            }
        }
        return true
    }

    private fun returnSingleChoiceQuestion() {
        singleChoiceQuestion = Question(
            stemEditText.text.toString(),
            "单选",
            score,
            answerId.toString(),
            optionList.size,
            optionList
        )
        if (getIsReturnableState()) {
            //TODO:还是写进缓存比较靠谱
            QuestionairePreference.q1Question = singleChoiceQuestion
        } else {
            val message: String = if (singleChoiceQuestion.stem.isEmpty()) {
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

    private class SingleOptionAdapter() :
        RecyclerView.Adapter<SingleOptionViewHolder>() {

        private lateinit var onDeleteListener: OnDeleteListener
        var isDeleting = false

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

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleOptionViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.q1_e_single_option, parent, false)
            val editText = itemView.findViewById<EditText>(R.id.q1_e_single_option_edit_text)!!
            val deleteButton = itemView.findViewById<ImageView>(R.id.q1_e_single_delete)!!
            return SingleOptionViewHolder(
                itemView,
                editText,
                deleteButton
            )
        }

        override fun getItemCount(): Int {
            return optionList.size
        }

        override fun onBindViewHolder(holder: SingleOptionViewHolder, position: Int) {
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

    private class SingleOptionViewHolder(
        itemView: View,
        val editText: EditText,
        val deleteButton: ImageView
    ) : RecyclerView.ViewHolder(itemView)
}