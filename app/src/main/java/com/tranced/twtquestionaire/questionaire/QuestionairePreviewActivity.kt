package com.tranced.twtquestionaire.questionaire

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.tranced.twtquestionaire.R

class QuestionairePreviewActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.q1_p_activity)
        toolbar = findViewById(R.id.common_toolbar)
        toolbar.title = "" //TODO: 这里是问卷名
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}
