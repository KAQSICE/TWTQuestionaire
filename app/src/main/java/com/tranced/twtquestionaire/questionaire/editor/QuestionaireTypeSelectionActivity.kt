package com.tranced.twtquestionaire.questionaire.editor

import android.os.Bundle
import android.view.Display
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.tranced.twtquestionaire.R

class QuestionaireTypeSelectionActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var display: Display

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.questionaire_editor_type_selection_activity)
        toolbar = findViewById(R.id.common_toolbar)
        toolbar.title = "添加题目"
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}
