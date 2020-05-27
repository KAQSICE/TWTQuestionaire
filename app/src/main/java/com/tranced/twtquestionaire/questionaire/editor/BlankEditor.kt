package com.tranced.twtquestionaire.questionaire.editor

import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.tranced.twtquestionaire.Question
import com.tranced.twtquestionaire.R
import com.tranced.twtquestionaire.questionaire.QuestionairePreference
import es.dmoral.toasty.Toasty
import org.jetbrains.anko.sdk27.coroutines.onClick

class BlankEditor : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var stemEditText: EditText
    private lateinit var compulsionSwitch: Switch
    private lateinit var answerButton: TextView
    private lateinit var scoreButton: TextView
    private lateinit var conditionButton: TextView
    private lateinit var createButton: Button
    private var answer = ""
    private var score: Int = 0
    private lateinit var blankQuestion: Question

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.q1_e_blank)
        findViews()
        setToolbar()
        setOnClickListeners()
    }

    private fun findViews() {
        toolbar = findViewById(R.id.common_toolbar)
        toolbarTitle = findViewById(R.id.common_toolbar_title)
        stemEditText = findViewById(R.id.q1_e_blank_input_question)
        compulsionSwitch = findViewById(R.id.q1_e_blank_compulsion_switch)
        answerButton = findViewById(R.id.q1_e_blank_answer_button)
        scoreButton = findViewById(R.id.q1_e_blank_score_button)
        conditionButton = findViewById(R.id.q1_e_blank_condition_button)
        createButton = findViewById(R.id.q1_e_blank_create)
    }

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
                if (it.itemId == R.id.q1_e_multiple_toolbar_create) {
                    finish()
                    //TODO:判断一下如果为空就返回null否则返回question
                }
                return@setOnMenuItemClickListener true
            }
        }
    }

    private fun setOnClickListeners() {
        answerButton.onClick {
            val builder = QMUIDialog.EditTextDialogBuilder(this@BlankEditor)
            builder.setInputType(InputType.TYPE_CLASS_TEXT)
                .setTitle("输入答案")
                .setPlaceholder("在此输入答案")
                .addAction(
                    "取消"
                ) { dialog, _ -> dialog!!.dismiss() }
                .addAction(
                    "确定"
                ) { dialog, _ ->
                    if (builder.editText.text.toString()
                            .isEmpty()
                    ) answerButton.setText(R.string.q1_e_unsettled)
                    else answerButton.setText(R.string.q1_e_settled)
                    answer = builder.editText.text.toString()
                    dialog!!.dismiss()
                }
                .show()
        }
        scoreButton.onClick {
            val builder = QMUIDialog.EditTextDialogBuilder(this@BlankEditor)
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
                        Toasty.error(this@BlankEditor, "请输入分值").show()
                        scoreButton.setText(R.string.q1_e_unsettled)
                    }
                }
                .show()
            builder.editText.setText(score.toString())
        }
        createButton.onClick {
            returnBlankQuestion()
        }
    }

    private fun getIsReturnableState(): Boolean {
        return stemEditText.text.toString().isNotEmpty()
    }

    private fun returnBlankQuestion() {
        if (getIsReturnableState()) {
            blankQuestion = Question(
                stemEditText.text.toString(),
                "填空",
                score,
                answer,
                0,
                mutableListOf()
            )
            QuestionairePreference.q1Question = blankQuestion
            setResult(103)
            finish()
        } else {
            Toasty.error(this, "题干不能为空", Toasty.LENGTH_SHORT).show()
        }
    }
}
