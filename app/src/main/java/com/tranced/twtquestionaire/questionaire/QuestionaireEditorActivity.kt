package com.tranced.twtquestionaire.questionaire

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.ItemAdapter
import cn.edu.twt.retrox.recyclerviewdsl.ItemManager
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.tranced.twtquestionaire.GlobalPreference
import com.tranced.twtquestionaire.Paper
import com.tranced.twtquestionaire.R
import com.tranced.twtquestionaire.questionaire.editor.AddItemButton
import com.tranced.twtquestionaire.questionaire.editor.addAddItemButton
import com.tranced.twtquestionaire.questionaire.editor.addInfo
import com.tranced.twtquestionaire.questionaire.editor.addSavePaperButton
import com.tranced.twtquestionaire.questionaire.item.*

class QuestionaireEditorActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var itemList: RecyclerView
    private lateinit var questionaire: Paper
    private lateinit var itemManager: ItemManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.q1_e_activity)
        getQuestionaireInfo()
        findViews()
        setToolbar()
        initItemList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.q1_e_toolbar_menu, menu)
        return true
    }

    private fun findViews() {
        toolbar = findViewById(R.id.common_toolbar)
        toolbarTitle = findViewById(R.id.common_toolbar_title)
        itemList = findViewById(R.id.q1_e_item_list)
    }

    private fun getQuestionaireInfo() {
        questionaire = GlobalPreference.q1Paper!!
    }

    private fun setToolbar() {
        toolbar.title = ""
        toolbarTitle.text = "编辑问卷"
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
            setOnMenuItemClickListener {
                //TODO:这里是预览还是设置
                if (it.itemId == R.id.q1_e_toolbar_preview) {
                    //TODO:要跳转至预览界面
                    val previewIntent = Intent(
                        this@QuestionaireEditorActivity,
                        QuestionairePreviewActivity::class.java
                    )
                    startActivity(previewIntent)
                }
                return@setOnMenuItemClickListener true
            }
        }
    }

    private fun initItemList() {
        itemList.apply {
            layoutManager = LinearLayoutManager(this@QuestionaireEditorActivity)
            withItems {
                clear()
                addInfo(questionaire.title, questionaire.description, questionaire.type)
                for (question in questionaire.questions) {
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
                    if (questionaire.questions.size > 0) {
                        GlobalPreference.createdPapers.add(
                            questionaire
                        )
                    }
                })
                itemManager = ItemManager(this)
            }
            adapter = ItemAdapter(itemManager)
            itemManager.apply {
                refreshAddItemButton(this)
            }
        }
    }

    //TODO:其实大可不必这么麻烦，跟那个保存按钮做成一样的就行
    private fun refreshAddItemButton(itemManager: ItemManager) {
        itemManager.apply {
            addAddItemButton(View.OnClickListener {
                if (last() is AddItemButton) {
                    val intent = Intent(
                        this@QuestionaireEditorActivity,
                        QuestionaireTypeSelectionActivity::class.java
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    startActivityForResult(intent, 0)
                    removeAt(size - 1)
                    //TODO: 这里添加对应item
                    refreshAddItemButton(itemManager)
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            1 -> {
                if (GlobalPreference.question != null) {
                    questionaire.questions.add(GlobalPreference.question!!)
                    initItemList()
                }
            }
            2 -> {
                if (GlobalPreference.question != null) {
                    questionaire.questions.add(GlobalPreference.question!!)
                    initItemList()
                }
            }
            3 -> {
                if (GlobalPreference.question != null) {
                    questionaire.questions.add(GlobalPreference.question!!)
                    initItemList()
                }
            }
            4 -> {
                if (GlobalPreference.question != null) {
                    questionaire.questions.add(GlobalPreference.question!!)
                    initItemList()
                }
            }
            5 -> {
                if (GlobalPreference.question != null) {
                    questionaire.questions.add(GlobalPreference.question!!)
                    initItemList()
                }
            }
            6 -> {
                if (GlobalPreference.question != null) {
                    questionaire.questions.add(GlobalPreference.question!!)
                    initItemList()
                }
            }
        }
    }
}
