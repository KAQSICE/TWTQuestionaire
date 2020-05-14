package com.tranced.twtquestionaire.questionaire

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.ItemAdapter
import cn.edu.twt.retrox.recyclerviewdsl.ItemManager
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.tranced.twtquestionaire.Paper
import com.tranced.twtquestionaire.R
import com.tranced.twtquestionaire.questionaire.editor.AddItemButton
import com.tranced.twtquestionaire.questionaire.editor.Constants.Companion.blankRequestCode
import com.tranced.twtquestionaire.questionaire.editor.Constants.Companion.multipleRequestCode
import com.tranced.twtquestionaire.questionaire.editor.Constants.Companion.paragraphRequestCode
import com.tranced.twtquestionaire.questionaire.editor.Constants.Companion.ratingRequestCode
import com.tranced.twtquestionaire.questionaire.editor.Constants.Companion.singleRequestCode
import com.tranced.twtquestionaire.questionaire.editor.Constants.Companion.sortRequestCode
import com.tranced.twtquestionaire.questionaire.editor.addAddItemButton
import com.tranced.twtquestionaire.questionaire.editor.addInfo

class QuestionaireEditorActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
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
        itemList = findViewById(R.id.q1_e_item_list)
    }

    private fun getQuestionaireInfo() {
        questionaire = QuestionairePreference.q1Paper!!
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
                addInfo(questionaire.title, questionaire.description)
                itemManager = ItemManager(this)
            }
            adapter = ItemAdapter(itemManager)
            itemManager.apply {
                refreshAddItemButton(this)
            }
        }
    }

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
                    Toast.makeText(baseContext, "我就弹个窗试试", Toast.LENGTH_SHORT).show()
                    removeAt(size - 1)
                    //TODO: 这里添加对应item
                    refreshAddItemButton(itemManager)
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            singleRequestCode -> {
                //TODO: 这里应该addSingleChoiceItem(data.getSerializableExtra("single"))
            }
            multipleRequestCode -> {

            }
            blankRequestCode -> {

            }
            paragraphRequestCode -> {

            }
            ratingRequestCode -> {

            }
            sortRequestCode -> {

            }
        }
        data?.getSerializableExtra("single")
    }
}