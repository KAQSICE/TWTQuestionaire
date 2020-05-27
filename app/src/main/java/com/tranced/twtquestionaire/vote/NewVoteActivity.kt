package com.tranced.twtquestionaire.vote

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.tranced.twtquestionaire.GlobalPreference
import com.tranced.twtquestionaire.Paper
import com.tranced.twtquestionaire.R
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.*

class NewVoteActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var beginEditText: EditText
    private lateinit var endEditText: EditText
    private lateinit var createBtn: Button
    private var beginDate: Date? = null
    private var endDate: Date? = null
    private var calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_vote_activity)
        findViews()
        setToolbar()
        setBeginAndEndDatePicker()
        setCreateBtnOnClickListener()
    }

    private fun findViews() {
        toolbar = findViewById(R.id.common_toolbar)
        toolbarTitle = findViewById(R.id.common_toolbar_title)
        titleEditText = findViewById(R.id.new_vote_title)
        descriptionEditText = findViewById(R.id.new_vote_description)
        beginEditText = findViewById(R.id.new_vote_begin)
        endEditText = findViewById(R.id.new_vote_end)
        createBtn = findViewById(R.id.new_vote_create_btn)
    }

    private fun setToolbar() {
        toolbar.title = ""
        toolbarTitle.text = "新建投票"
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setBeginAndEndDatePicker() {
        beginEditText.apply {
            isFocusable = false
            isLongClickable = false
            onClick {
                val onDateSetListener =
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        val realMonth: Int = month + 1
                        val beginDateText = "$year" + "年$realMonth" + "月$dayOfMonth" + "日"
                        beginDate = Date(year, month, dayOfMonth)
                        beginEditText.setText(beginDateText.toCharArray(), 0, beginDateText.length)
                    }
                val dialog = DatePickerDialog(
                    this@NewVoteActivity,
                    onDateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                dialog.show()
            }
        }
        endEditText.apply {
            isFocusable = false
            isLongClickable = false
            onClick {
                val onDateSetListener =
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        val realMonth: Int = month + 1
                        val endDateText = "$year" + "年$realMonth" + "月$dayOfMonth" + "日"
                        endDate = Date(year, month, dayOfMonth)
                        endEditText.setText(endDateText.toCharArray(), 0, endDateText.length)
                    }
                val dialog = DatePickerDialog(
                    this@NewVoteActivity,
                    onDateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                dialog.show()
            }
        }
    }

    private fun setCreateBtnOnClickListener() {
        createBtn.onClick {
            //TODO:这里还要判断一下日期先后
            if (titleEditText.text.toString().isEmpty()) {
                AlertDialog.Builder(this@NewVoteActivity)
                    .setTitle("警告")
                    .setMessage("问卷名称不能为空")
                    .setPositiveButton("确定", null)
                    .show()
            } else {
                AlertDialog.Builder(this@NewVoteActivity)
                    .setTitle("是否创建")
                    .setPositiveButton("确定") { _, _ ->
                        val intent =
                            Intent(
                                this@NewVoteActivity,
                                VoteEditorActivity::class.java
                            )
                        val vote = Paper(
                            titleEditText.text.toString(),
                            "投票",
                            when (descriptionEditText.text.isNullOrEmpty()) {
                                true -> ""
                                else -> descriptionEditText.text.toString()
                            },
                            "-1",
                            "-1",
                            0,
                            when (endDate) {
                                null -> -1
                                else -> {
                                    if (endDate!!.time > System.currentTimeMillis()) {
                                        ((endDate!!.time - System.currentTimeMillis()) / (3600000 * 24)).toInt()
                                    } else {
                                        -1
                                    }
                                }
                            },
                            "TODO",
                            0,
                            mutableListOf()
                        )
                        GlobalPreference.vPaper = vote
                        finish()
                        startActivity(intent)
                    }
                    .setNegativeButton("取消", null)
                    .show()
            }
        }
    }
}
