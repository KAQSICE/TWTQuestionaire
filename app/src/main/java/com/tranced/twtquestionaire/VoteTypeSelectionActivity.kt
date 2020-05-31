package com.tranced.twtquestionaire

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.tranced.twtquestionaire.editor.BlankEditor
import com.tranced.twtquestionaire.editor.MultipleChoiceEditor
import com.tranced.twtquestionaire.editor.SingleChoiceEditor
import es.dmoral.toasty.Toasty
import org.jetbrains.anko.sdk27.coroutines.onClick

class VoteTypeSelectionActivity : AppCompatActivity() {
    private val nullResultCode: Int = 400
    private val singleResultCode: Int = 101
    private val multipleResultCode: Int = 102
    private val blankResultCode: Int = 103

    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var singleCardView: CardView
    private lateinit var multipleCardView: CardView
    private lateinit var blankCardView: CardView
    private lateinit var importButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.v_e_type_selection_activity)
        setToolbar()
        findViews()
        setOnClickListeners()
    }

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
        singleCardView = findViewById(R.id.v_e_type_selection_single)
        multipleCardView = findViewById(R.id.v_e_type_selection_multiple)
        blankCardView = findViewById(R.id.v_e_type_selection_blank)
        importButton = findViewById(R.id.v_e_type_selection_import)
    }

    private fun setOnClickListeners() {
        singleCardView.onClick {
            val intent =
                Intent(this@VoteTypeSelectionActivity, SingleChoiceEditor::class.java)
            startActivityForResult(intent, 101)
        }
        multipleCardView.onClick {
            val intent =
                Intent(this@VoteTypeSelectionActivity, MultipleChoiceEditor::class.java)
            startActivityForResult(intent, 102)
        }
        blankCardView.onClick {
            val intent =
                Intent(this@VoteTypeSelectionActivity, BlankEditor::class.java)
            startActivityForResult(intent, 103)
        }
        importButton.onClick {
            Toasty.info(this@VoteTypeSelectionActivity, "Work In Progress", Toasty.LENGTH_SHORT)
                .show()
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
        }
    }
}
