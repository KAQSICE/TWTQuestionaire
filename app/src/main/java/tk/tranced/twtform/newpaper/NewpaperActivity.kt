@file:Suppress("DEPRECATION")

package tk.tranced.twtform.newpaper

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.orhanobut.hawk.Hawk
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import es.dmoral.toasty.Toasty
import org.jetbrains.anko.sdk27.coroutines.onClick
import tk.tranced.twtform.R
import tk.tranced.twtform.data.Paper
import tk.tranced.twtform.editor.PreviewActivity
import tk.tranced.twtform.preference.GlobalPreference
import java.util.*

/**
 * NewpaperActivity
 * @author TranceD
 */
class NewpaperActivity : AppCompatActivity() {
    private var paperType = 0
    private val paperTypeText = arrayOf("问卷", "答题", "投票")
    private var beginDate: Date? = null
    private var endDate: Date? = null
    private val calendar = Calendar.getInstance()
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var titleLabel: TextView
    private lateinit var titleEditText: EditText
    private lateinit var descriptionLabel: TextView
    private lateinit var descriptionEditText: EditText
    private lateinit var beginLabel: TextView
    private lateinit var beginEditText: EditText
    private lateinit var endLabel: TextView
    private lateinit var endEditText: EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.newpaper_activity)
        paperType = intent.getIntExtra("paperType", 0)
        Hawk.init(this).build()
        findViews()
        setToolbar()
        setLabelText()
        setDatePicker()
        setButtonOnClickListener()
    }

    private fun findViews() {
        toolbar = findViewById(R.id.commonToolbar)
        toolbarTitle = findViewById(R.id.commonToolbarTitle)
        titleLabel = findViewById(R.id.newpaperTitleLabel)
        titleEditText = findViewById(R.id.newpaperTitleEditText)
        descriptionLabel = findViewById(R.id.newpaperDescriptionLabel)
        descriptionEditText = findViewById(R.id.newpaperDescriptionEditText)
        beginLabel = findViewById(R.id.newpaperBeginLabel)
        beginEditText = findViewById(R.id.newpaperBeginEditText)
        endLabel = findViewById(R.id.newpaperEndLabel)
        endEditText = findViewById(R.id.newpaperEndEditText)
        button = findViewById(R.id.newpaperButton)

    }

    @SuppressLint("SetTextI18n")
    private fun setToolbar() {
        toolbar.title = ""
        toolbarTitle.text = "新建${paperTypeText[paperType]}"
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setLabelText() {
        titleLabel.text = "${paperTypeText[paperType]}名称"
        titleEditText.hint = "请输入${paperTypeText[paperType]}名称"
        descriptionLabel.text = "${paperTypeText[paperType]}说明"
        descriptionEditText.hint = "请输入${paperTypeText[paperType]}说明"
        beginLabel.text = "开始日期"
        beginEditText.hint = "请选择开始日期"
        endLabel.text = "截止日期"
        endEditText.hint = "请选择截止日期"
        button.text = "创建${paperTypeText[paperType]}"
    }

    private fun setDatePicker() {
        beginEditText.apply {
            isFocusable = false
            isLongClickable = false
            onClick {
                val onDateSetListener =
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        val beginDateText = "${year}年${month + 1}月${dayOfMonth}日"
                        beginDate = Date(year, month, dayOfMonth)
                        beginEditText.setText(beginDateText.toCharArray(), 0, beginDateText.length)
                    }
                val dialog = DatePickerDialog(
                    this@NewpaperActivity,
                    onDateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                dialog.show()
            }
        }
        endEditText.apply {
            isFocusable = false
            isLongClickable = false
            onClick {
                val onDateSetListener =
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        val endDateText = "${year}年${month + 1}月${dayOfMonth}日"
                        endDate = Date(year, month, dayOfMonth)
                        endEditText.setText(endDateText.toCharArray(), 0, endDateText.length)
                    }
                val dialog = DatePickerDialog(
                    this@NewpaperActivity,
                    onDateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                dialog.show()
            }
        }
    }

    private fun setButtonOnClickListener() {
        button.onClick {
            if (titleEditText.text.toString().isEmpty()) {
                Toasty.error(this@NewpaperActivity, "问卷名称不能为空")
            } else {
                QMUIDialog.MessageDialogBuilder(this@NewpaperActivity)
                    .setMessage("是否创建")
                    .addAction("取消") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .addAction("确定") { _, _ ->
                        val intent =
                            Intent(
                                this@NewpaperActivity,
                                PreviewActivity::class.java
                            )
                        intent.putExtra("paperType", paperType)
                        GlobalPreference.paper = Paper(
                            -1, //TODO:这里是id(?
                            titleEditText.text.toString(),
                            paperType,
                            when (descriptionEditText.text.isNullOrEmpty()) {
                                true -> null
                                false -> descriptionEditText.text.toString()
                            },
                            when (beginDate) {
                                null -> null
                                else -> {
                                    beginDate!!.time / 1000
                                }
                            },
                            when (endDate) {
                                null -> null
                                else -> {
                                    endDate!!.time / 1000
                                }
                            },
                            mutableListOf(),
                            0,
                            -1
                        )
                        finish()
                        startActivity(intent)
                    }
                    .show()
            }
        }
    }
}