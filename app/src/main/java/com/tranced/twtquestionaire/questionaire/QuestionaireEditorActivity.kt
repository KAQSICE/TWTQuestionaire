package com.tranced.twtquestionaire.questionaire

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.tranced.twtquestionaire.R
import com.tranced.twtquestionaire.questionaire.editor.addAddItemButton
import com.tranced.twtquestionaire.questionaire.editor.addInfo
import java.util.*

class QuestionaireEditorActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var itemList: RecyclerView
    private lateinit var questionaire: Questionaire

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.questionaire_editor_activity)
        getQuestionaireInfo()
        findViews()
        setToolbar()
        initItemList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.questionaire_editor_toolbar_menu, menu)
        return true
    }

    private fun findViews() {
        toolbar = findViewById(R.id.common_toolbar)
        itemList = findViewById(R.id.questionaire_editor_item_list)
    }

    private fun getQuestionaireInfo() {
        questionaire = Questionaire(
            intent.getStringExtra("title"),
            intent.getStringExtra("description"),
            intent.getSerializableExtra("beginDate") as Date?,
            intent.getSerializableExtra("endDate") as Date?
        )
    }

    private fun setToolbar() {
        toolbar.title = "编辑问卷"
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
                if (it.itemId == R.id.questionaire_editor_toolbar_preview) {
                    //TODO:要跳转至预览界面
                    val previewIntent = Intent()
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
                addInfo(questionaire.title, questionaire.description)
                addAddItemButton()
                this[adapter?.itemCount?.minus(1)!!].controller
            }
        }
    }
}
