package com.tranced.twtquestionaire.questionaire.editor

import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.tranced.twtquestionaire.Option
import com.tranced.twtquestionaire.Question
import com.tranced.twtquestionaire.R

class MultipleChoiceEditor : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var optionListRecyclerView: RecyclerView
    private lateinit var addOptionButton: Button
    private lateinit var multipleChoiceQuestion: Question
    private val mOptionList = mutableListOf<Option>()
    private var isReturnable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.q1_e_multiple_choice)
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

    private fun findViews() {
        toolbar = findViewById(R.id.common_toolbar)
        optionListRecyclerView = findViewById(R.id.q1_e_multiple_option_list)
        addOptionButton = findViewById(R.id.q1_e_multiple_add_option)
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
                if (it.itemId == R.id.q1_e_multiple_toolbar_create) {
                    finish()
                    //TODO:判断一下如果为空就返回null否则返回question
                }
                return@setOnMenuItemClickListener true
            }
        }
    }

    private fun initOptionListRecyclerView() {

    }

    private fun getIsReturnableState() {

    }
}
