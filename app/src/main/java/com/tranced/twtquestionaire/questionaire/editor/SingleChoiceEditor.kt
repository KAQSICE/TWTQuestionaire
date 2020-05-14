package com.tranced.twtquestionaire.questionaire.editor

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tranced.twtquestionaire.Option
import com.tranced.twtquestionaire.Question
import com.tranced.twtquestionaire.R
import org.jetbrains.anko.sdk27.coroutines.onClick


class SingleChoiceEditor : AppCompatActivity() {
    private lateinit var singleChoiceQuestion: Question
    private lateinit var toolbar: Toolbar
    private lateinit var optionListRecyclerView: RecyclerView
    private lateinit var addOptionButton: Button
    private val mOptionList: MutableList<Option> = mutableListOf()
    private var isReturnable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.q1_e_single_choice)
        setActivitySize()
        findViews()
        setToolbar()
        initOptionListRecyclerView()
        getIsReturnableState()
    }

    @Suppress("DEPRECATION")
    private fun setActivitySize() {
        val display = windowManager.defaultDisplay
        val layoutParams: WindowManager.LayoutParams = window.attributes
        layoutParams.apply {
            width = (display.width * 0.8).toInt()
            height = (display.height * 0.75).toInt()
        }
    }

    private fun setToolbar() {
        toolbar.title = "编辑题目"
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
        optionListRecyclerView = findViewById(R.id.q1_e_single_option_list)
        addOptionButton = findViewById(R.id.q1_e_single_add_option)
    }

    private fun initOptionListRecyclerView() {
        repeat(3) {
            mOptionList.add(Option("", it, ""))
        }
        optionListRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@SingleChoiceEditor)
            adapter =
                SingleOptionAdapter(
                    mOptionList
                )
            (adapter as SingleOptionAdapter).setOnDeleteListener(object :
                SingleOptionAdapter.OnDeleteListener {
                override fun onDelete(position: Int) {
                    (adapter as SingleOptionAdapter).apply {
                        delete(position)
                        optionListRecyclerView.adapter = this
                        isDeleting = false
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

    private fun getIsReturnableState() {
    }

    private fun returnSingleChoiceQuestion() {
        if (isReturnable) {
            //TODO:还是写进缓存比较靠谱
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

    private class SingleOptionAdapter(val optionList: MutableList<Option>) :
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