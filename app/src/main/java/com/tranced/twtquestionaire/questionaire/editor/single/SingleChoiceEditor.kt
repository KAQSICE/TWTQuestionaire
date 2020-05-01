package com.tranced.twtquestionaire.questionaire.editor.single

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.tranced.twtquestionaire.Question
import com.tranced.twtquestionaire.R

class SingleChoiceEditor : AppCompatActivity() {
    private lateinit var singleChoiceQuestion: Question
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.q1_e_single_choice)
        setActivitySize()
        findViews()
        setToolbar()
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
    }
}
