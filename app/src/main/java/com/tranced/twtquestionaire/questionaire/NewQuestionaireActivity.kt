package com.tranced.twtquestionaire.questionaire

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.tranced.twtquestionaire.R
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.*

class NewQuestionaireActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
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
        setContentView(R.layout.new_questionaire_activity)
        findViews()
        setToolbar()
        setBeginAndEndDatePicker()
        setCreateBtnOnClickListener()
    }

    private fun findViews() {
        toolbar = findViewById(R.id.common_toolbar)
        titleEditText = findViewById(R.id.new_questionaire_title)
        descriptionEditText = findViewById(R.id.new_questionaire_description)
        beginEditText = findViewById(R.id.new_questionaire_begin)
        endEditText = findViewById(R.id.new_questionaire_end)
        createBtn = findViewById(R.id.new_questionaire_create_btn)
    }

    private fun setToolbar() {
        toolbar.title = "新建问卷"
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
                    this@NewQuestionaireActivity,
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
                    this@NewQuestionaireActivity,
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
                AlertDialog.Builder(this@NewQuestionaireActivity)
                    .setTitle("警告")
                    .setMessage("问卷名称不能为空")
                    .setPositiveButton("确定", null)
                    .show()
            } else {
                AlertDialog.Builder(this@NewQuestionaireActivity)
                    .setTitle("是否创建")
                    .setPositiveButton("确定") { _, _ ->
                        val intent =
                            Intent(
                                this@NewQuestionaireActivity,
                                QuestionaireEditorActivity::class.java
                            )
                        intent.putExtra("title", titleEditText.text.toString())
                        if (descriptionEditText.text.isNullOrEmpty()) {
                            intent.putExtra("description", "")
                        } else {
                            intent.putExtra("description", descriptionEditText.text.toString())
                        }
                        intent.putExtra("beginDate", beginDate)
                        intent.putExtra("endDate", endDate)
                        finish()
                        startActivity(intent)
                    }
                    .setNegativeButton("取消", null)
                    .show()
            }
        }
    }
}
