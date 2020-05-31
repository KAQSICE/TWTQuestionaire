package com.tranced.twtquestionaire.quiz
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.tranced.twtquestionaire.R
import com.tranced.twtquestionaire.data.GlobalPreference
import com.tranced.twtquestionaire.data.Paper
import es.dmoral.toasty.Toasty
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.*

class NewQuizActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var titleEditText: EditText
    private lateinit var titleHintText: TextView
    private lateinit var descriptionEditText: EditText
    private lateinit var descriptionHintText: TextView
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
        setHintText()
        setBeginAndEndDatePicker()
        setCreateBtnOnClickListener()
    }

    private fun findViews() {
        toolbar = findViewById(R.id.common_toolbar)
        toolbarTitle = findViewById(R.id.common_toolbar_title)
        titleEditText = findViewById(R.id.new_questionaire_title)
        titleHintText = findViewById(R.id.new_questionaire_title_hint)
        descriptionEditText = findViewById(R.id.new_questionaire_description)
        descriptionHintText = findViewById(R.id.new_questionaire_description_hint)
        beginEditText = findViewById(R.id.new_questionaire_begin)
        endEditText = findViewById(R.id.new_questionaire_end)
        createBtn = findViewById(R.id.new_questionaire_create_btn)
    }

    private fun setToolbar() {
        toolbar.title = ""
        toolbarTitle.text = "新建答题"
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setHintText() {
        titleHintText.text = "答题名称"
        descriptionHintText.text = "答题说明"
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
                    this@NewQuizActivity,
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
                    this@NewQuizActivity,
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
                Toasty.error(this@NewQuizActivity, "答题标题不能为空", Toasty.LENGTH_SHORT).show()
            } else {
                QMUIDialog.MessageDialogBuilder(this@NewQuizActivity)
                    .setMessage("是否创建")
                    .addAction("取消") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .addAction("确认") { _, _ ->
                        val intent =
                            Intent(
                                this@NewQuizActivity,
                                QuizEditorActivity::class.java
                            )
                        val quiz =
                            Paper(
                                titleEditText.text.toString(),
                                "答题",
                                when (descriptionEditText.text.isNullOrEmpty()) {
                                    true -> ""
                                    else -> descriptionEditText.text.toString()
                                },
                                "-1",
                                "-1",
                                0,
                                -1,
                                "TODO",
                                0,
                                mutableListOf()
                            )
                        GlobalPreference.q2Paper = quiz
                        finish()
                        startActivity(intent)
                    }
                    .show()
            }
        }
    }
}
