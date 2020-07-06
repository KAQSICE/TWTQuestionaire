package tk.tranced.twtform.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import tk.tranced.twtform.R

class MainActivity : AppCompatActivity() {
    private lateinit var mainToolbar: Toolbar
    private lateinit var mainToolbarTitle: TextView
    private lateinit var mainSearchView: SearchView
    private lateinit var mainRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        findViews()
        setToolbarTitle()
        initMainRecyclerView()
    }

    private fun findViews() {
        mainToolbar = findViewById(R.id.commonToolbar)
        mainToolbarTitle = findViewById(R.id.commonToolbarTitle)
        mainSearchView = findViewById(R.id.mainSearchView)
        mainRecyclerView = findViewById(R.id.mainRecyclerView)
    }

    @SuppressLint("SetTextI18n")
    private fun setToolbarTitle() {
        mainToolbar.title = ""
        mainToolbarTitle.text = "TWT编辑后台"
        setSupportActionBar(mainToolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        mainToolbar.setNavigationOnClickListener {
            //TODO：这里跳转到登陆页面
        }
        mainToolbar.setNavigationIcon(R.mipmap.ic_person_white_36dp)
    }

    private fun initMainRecyclerView() {
        mainRecyclerView.layoutManager = LinearLayoutManager(this)
        mainRecyclerView.withItems {
            for (i in 0..4) {
                addMainItem(i)
            }
        }
    }
}