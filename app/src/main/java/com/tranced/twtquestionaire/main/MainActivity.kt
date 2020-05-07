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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.orhanobut.hawk.Hawk
import com.tranced.twtquestionaire.R
import com.tranced.twtquestionaire.questionaire.NewQuestionaireActivity
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
    private lateinit var questionaire: CardView
    private lateinit var vote: CardView
    private lateinit var quiz: CardView
    private lateinit var created: CardView
    private lateinit var participated: CardView
    private lateinit var trash: CardView
    private lateinit var star: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        findViews()
        initToolbarAndDrawerLayout()
        initDrawerLayout()
        setOnClickListeners()
        Hawk.init(this).build()
    }

    private fun findViews() {
        toolbar = findViewById(R.id.main_toolbar)
        drawerLayout = findViewById(R.id.main_drawerlayout)
        drawerList = findViewById(R.id.main_drawerlayout_list)
        questionaire = findViewById(R.id.main_drawerlayout_questionaire)
        vote = findViewById(R.id.main_drawerlayout_vote)
        quiz = findViewById(R.id.main_drawerlayout_quiz)
        created = findViewById(R.id.main_drawerlayout_created)
        participated = findViewById(R.id.main_drawerlayout_participated)
        trash = findViewById(R.id.main_drawerlayout_trash)
    }

    private fun initToolbarAndDrawerLayout() {
        toolbar.title = "问卷答题投票"
        drawerToggle = object : ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.open,
            R.string.close
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                Toast.makeText(baseContext, "open!!!", Toast.LENGTH_SHORT).show()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                Toast.makeText(baseContext, "close!!!", Toast.LENGTH_SHORT).show()
            }
        }
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
        questionaire.onClick {
            var intent = Intent(this@MainActivity, NewQuestionaireActivity::class.java)
            startActivity(intent)
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
