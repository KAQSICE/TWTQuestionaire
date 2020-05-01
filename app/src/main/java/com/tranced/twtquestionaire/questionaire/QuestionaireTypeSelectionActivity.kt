package com.tranced.twtquestionaire.questionaire

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.tranced.twtquestionaire.R
import com.tranced.twtquestionaire.questionaire.editor.single.SingleChoiceEditor
import org.jetbrains.anko.sdk27.coroutines.onClick

class QuestionaireTypeSelectionActivity : AppCompatActivity() {
    private val singleRequestCode: Int = 101
    private val multipleRequestCode: Int = 102

    private lateinit var toolbar: Toolbar
    private lateinit var singleCardView: CardView
    private lateinit var multipleCardView: CardView
    private lateinit var blankCardView: CardView
    private lateinit var paragraphCardView: CardView
    private lateinit var ratingCardView: CardView
    private lateinit var sortCardView: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.q1_e_type_selection_activity)
        setActivitySize()
        setToolbar()
        findViews()
        setOnClickListeners()
    }

    @Suppress("DEPRECATION")
    private fun setActivitySize() {
        val display = windowManager.defaultDisplay
        val layoutParams: WindowManager.LayoutParams = window.attributes
        layoutParams.apply {
            width = (display.width * 0.8).toInt()
            height = (display.height * 0.5).toInt()
        }
    }

    private fun setToolbar() {
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

    private fun findViews() {
        singleCardView = findViewById(R.id.q1_e_type_selection_single)
        multipleCardView = findViewById(R.id.q1_e_type_selection_multiple)
        blankCardView = findViewById(R.id.q1_e_type_selection_blank)
        paragraphCardView = findViewById(R.id.q1_e_type_selection_paragraph)
        ratingCardView = findViewById(R.id.q1_e_type_selection_rating)
        sortCardView = findViewById(R.id.q1_e_type_selection_sort)
    }

    private fun setOnClickListeners() {
        singleCardView.onClick {
            finish()
            val intent =
                Intent(this@QuestionaireTypeSelectionActivity, SingleChoiceEditor::class.java)
            startActivityForResult(intent, 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intent = Intent()
        when (resultCode) {
            singleRequestCode -> {
                intent.putExtra("single", 1)
                setResult(singleRequestCode, intent)
            }
            multipleRequestCode -> {
                intent.putExtra("multiple", 2)
                setResult(multipleRequestCode, intent)
            }
        }
    }
}
