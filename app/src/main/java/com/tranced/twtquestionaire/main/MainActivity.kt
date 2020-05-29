package com.tranced.twtquestionaire.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.orhanobut.hawk.Hawk
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import com.tranced.twtquestionaire.R
import com.tranced.twtquestionaire.created.CreatedActivity
import com.tranced.twtquestionaire.data.getData
import com.tranced.twtquestionaire.participated.ParticipatedActivity
import com.tranced.twtquestionaire.questionaire.NewQuestionaireActivity
import com.tranced.twtquestionaire.quiz.NewQuizActivity
import com.tranced.twtquestionaire.star.StarActivity
import com.tranced.twtquestionaire.vote.NewVoteActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * MainActivity
 * @author TranceD
 */
class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var drawerList: ListView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var search: SearchView
    private lateinit var new: CardView
    private lateinit var created: CardView
    private lateinit var participated: CardView
    private lateinit var trash: CardView
    private lateinit var star: CardView
    private val newItemsIntent: MutableList<Intent> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        findViews()
        initToolbarAndDrawerLayout()
        initDrawerLayout()
        setOnClickListeners()
        setSwipeRefreshLayout()
        Hawk.init(this).build()
        getData()
    }

    private fun findViews() {
        toolbar = findViewById(R.id.main_toolbar)
        drawerLayout = findViewById(R.id.main_drawerlayout)
        drawerList = findViewById(R.id.main_drawerlayout_list)
        swipeRefreshLayout = findViewById(R.id.main_drawerlayout_swipe_refresh)
        search = findViewById(R.id.main_drawerlayout_search)
        new = findViewById(R.id.main_drawerlayout_new)
        created = findViewById(R.id.main_drawerlayout_created)
        participated = findViewById(R.id.main_drawerlayout_participated)
        star = findViewById(R.id.main_drawerlayout_star)
        trash = findViewById(R.id.main_drawerlayout_trash)
    }

    private fun initToolbarAndDrawerLayout() {
        toolbar.title = ""
        drawerToggle = object : ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.open,
            R.string.close
        ) {}
        drawerToggle.apply {
            syncState()
            isDrawerIndicatorEnabled = true
        }
        drawerLayout.addDrawerListener(drawerToggle)


        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        toolbar.setNavigationIcon(R.mipmap.main_navigation_icon)
    }

    private fun initDrawerLayout() {
        val data = ArrayList<DrawerItem>()
        for (i in 1..10) {
            data.add(DrawerItem("Item$i", 0))
        }
        val drawerItemAdapter =
            DrawerItemAdapter(baseContext, R.layout.main_drawerlayout_item, data)
        drawerList.adapter = drawerItemAdapter
    }

    private fun setOnClickListeners() {
        val newItemsText = arrayOf("问卷", "投票", "答题")
        newItemsIntent.addAll(
            mutableListOf(
                Intent(this@MainActivity, NewQuestionaireActivity::class.java),
                Intent(this@MainActivity, NewVoteActivity::class.java),
                Intent(this@MainActivity, NewQuizActivity::class.java)
            )
        )
        new.onClick {
            QMUIBottomSheet.BottomListSheetBuilder(this@MainActivity)
                .addItem(newItemsText[0])
                .addItem(newItemsText[1])
                .addItem(newItemsText[2])
                .setOnSheetItemClickListener { dialog, _, position, _ ->
                    dialog.dismiss()
                    startActivity(newItemsIntent[position])
                }
                .build()
                .show()
        }
        created.onClick {
            val intent = Intent(this@MainActivity, CreatedActivity::class.java)
            startActivity(intent)
        }
        participated.onClick {
            val intent = Intent(this@MainActivity, ParticipatedActivity::class.java)
            startActivity(intent)
        }
        star.onClick {
            val intent = Intent(this@MainActivity, StarActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setSwipeRefreshLayout() {
        swipeRefreshLayout.apply {
            setOnRefreshListener {
                GlobalScope.launch {
                    //TODO:这里要重新进行数据请求
                    delay(3000)
                }
                isRefreshing = false
            }
        }
    }
}

class DrawerItem(val content: String, val imgId: Int)

class DrawerItemAdapter(context: Context, private val tvId: Int, data: List<DrawerItem>) :
    ArrayAdapter<DrawerItem>(context, tvId, data) {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        val view = LayoutInflater.from(context)
            .inflate(tvId, parent, false)
        val tv = view.findViewById<TextView>(R.id.main_drawerlayout_item_text)
        if (item != null) {
            tv.text = item.content
        }
        return view
    }
}
