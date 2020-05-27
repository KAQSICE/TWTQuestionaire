package com.tranced.twtquestionaire.questionaire

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.tranced.twtquestionaire.R
import com.tranced.twtquestionaire.questionaire.editor.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class QuestionaireTypeSelectionActivity : AppCompatActivity() {
    private val nullResultCode: Int = 400
    private val singleResultCode: Int = 101
    private val multipleResultCode: Int = 102
    private val blankResultCode: Int = 103
    private val paragraphResultCode: Int = 104
    private val ratingResultCode: Int = 105
    private val sortResultCode: Int = 106

    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var singleCardView: CardView
    private lateinit var multipleCardView: CardView
    private lateinit var blankCardView: CardView
    private lateinit var paragraphCardView: CardView
    private lateinit var ratingCardView: CardView
    private lateinit var sortCardView: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.q1_e_type_selection_activity)
//        setActivitySize()
        setToolbar()
        findViews()
        setOnClickListeners()
    }
/*
还是做成全屏的
@Suppress("DEPRECATION")
private fun setActivitySize() {
val display = windowManager.defaultDisplay
val layoutParams: WindowManager.LayoutParams = window.attributes
layoutParams.apply {
width = (display.width * 0.8).toInt()
height = (display.height * 0.5).toInt()
}
}
*/

    private fun setToolbar() {
        toolbar = findViewById(R.id.common_toolbar)
        toolbarTitle = findViewById(R.id.common_toolbar_title)
        toolbar.title = ""
        toolbarTitle.text = "编辑题目"
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
            val intent =
                Intent(this@QuestionaireTypeSelectionActivity, SingleChoiceEditor::class.java)
            startActivityForResult(intent, 101)
        }
        multipleCardView.onClick {
            val intent =
                Intent(this@QuestionaireTypeSelectionActivity, MultipleChoiceEditor::class.java)
            startActivityForResult(intent, 102)
        }
        blankCardView.onClick {
            val intent =
                Intent(this@QuestionaireTypeSelectionActivity, BlankEditor::class.java)
            startActivityForResult(intent, 103)
        }
        paragraphCardView.onClick {
            val intent =
                Intent(this@QuestionaireTypeSelectionActivity, ParagraphEditor::class.java)
            startActivityForResult(intent, 104)
        }
        ratingCardView.onClick {
            val intent =
                Intent(this@QuestionaireTypeSelectionActivity, RatingEditor::class.java)
            startActivityForResult(intent, 105)
        }
        sortCardView.onClick {
            val intent =
                Intent(this@QuestionaireTypeSelectionActivity, SortEditor::class.java)
            startActivityForResult(intent, 106)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intent = Intent()
        when (resultCode) {
            nullResultCode -> {
                intent.putExtra("question", 0)
                setResult(nullResultCode, intent)
                finish()
            }
            singleResultCode -> {
                intent.putExtra("question", 1)
                setResult(1, intent)
                finish()
            }
            multipleResultCode -> {
                intent.putExtra("question", 2)
                setResult(2, intent)
                finish()
            }
            blankResultCode -> {
                intent.putExtra("question", 3)
                setResult(3, intent)
                finish()
            }
            paragraphResultCode -> {
                intent.putExtra("question", 4)
                setResult(4, intent)
                finish()
            }
            ratingResultCode -> {
                intent.putExtra("question", 5)
                setResult(5, intent)
                finish()
            }
            sortResultCode -> {
                intent.putExtra("question", 6)
                setResult(6, intent)
                finish()
            }
        }
    }
}
