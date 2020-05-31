package com.tranced.twtquestionaire.quiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.tranced.twtquestionaire.R
import com.tranced.twtquestionaire.TypeSelectionActivity
import com.tranced.twtquestionaire.data.GlobalPreference
import com.tranced.twtquestionaire.data.Paper
import com.tranced.twtquestionaire.editor.addAddItemButton
import com.tranced.twtquestionaire.editor.addInfo
import com.tranced.twtquestionaire.editor.addSavePaperButton
import com.tranced.twtquestionaire.item.*

class QuizEditorActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var itemList: RecyclerView
    private lateinit var quiz: Paper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.q1_e_activity)
        getQuestionaireInfo()
        findViews()
        setToolbar()
        initItemList()
    }

    private fun findViews() {
        toolbar = findViewById(R.id.common_toolbar)
        toolbarTitle = findViewById(R.id.common_toolbar_title)
        itemList = findViewById(R.id.q1_e_item_list)
    }

    private fun getQuestionaireInfo() {
        quiz = GlobalPreference.q2Paper!!
    }

    private fun setToolbar() {
        toolbar.title = ""
        toolbarTitle.text = "编辑答题"
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.apply {
            setNavigationOnClickListener {
                //TODO:这里应该问一句是否真的要退出
                finish()
            }
        }
    }

    private fun initItemList() {
        itemList.apply {
            layoutManager = LinearLayoutManager(this@QuizEditorActivity)
            withItems {
                clear()
                addInfo(quiz.title, quiz.description, quiz.type)
                for (question in quiz.questions) {
                    when (question.type) {
                        "单选" -> addSingleChoiceItem(question)
                        "多选" -> addMultipleChoiceItem(question)
                        "填空" -> addBlankItem(question)
                        "段落" -> addParagraphItem(question)
                        "量表" -> addRatingItem(question)
                        "排序" -> addSortItem(question)
                    }
                }
                addSavePaperButton(View.OnClickListener {
                    if (quiz.questions.size > 0) {
                        val builder = QMUIDialog.MessageDialogBuilder(this@QuizEditorActivity)
                        builder.setMessage("是否创建？")
                            .addAction("取消") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .addAction("确定") { dialog, _ ->
                                GlobalPreference.createdPapers.add(quiz)
                                dialog.dismiss()
                                finish()
                            }
                            .show()
                    }
                })
                addAddItemButton(View.OnClickListener {
                    val intent = Intent(
                        this@QuizEditorActivity,
                        TypeSelectionActivity::class.java
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    startActivityForResult(intent, 0)
                })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            1 -> {
                if (GlobalPreference.question != null) {
                    quiz.questions.add(GlobalPreference.question!!)
                    initItemList()
                }
            }
            2 -> {
                if (GlobalPreference.question != null) {
                    quiz.questions.add(GlobalPreference.question!!)
                    initItemList()
                }
            }
            3 -> {
                if (GlobalPreference.question != null) {
                    quiz.questions.add(GlobalPreference.question!!)
                    initItemList()
                }
            }
            4 -> {
                if (GlobalPreference.question != null) {
                    quiz.questions.add(GlobalPreference.question!!)
                    initItemList()
                }
            }
            5 -> {
                if (GlobalPreference.question != null) {
                    quiz.questions.add(GlobalPreference.question!!)
                    initItemList()
                }
            }
            6 -> {
                if (GlobalPreference.question != null) {
                    quiz.questions.add(GlobalPreference.question!!)
                    initItemList()
                }
            }
        }
    }
}
