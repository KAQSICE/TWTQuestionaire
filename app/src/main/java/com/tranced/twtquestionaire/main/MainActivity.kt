package com.tranced.twtquestionaire.main

import android.content.Context
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
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.tranced.twtquestionaire.R

/**
 * MainActivity
 * @author TranceD
 */
class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var drawerList: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        findViews()
        initToolbarAndDrawerLayout()
        initDrawerLayout()

    }

    private fun findViews() {
        toolbar = findViewById(R.id.main_toolbar)
        drawerLayout = findViewById(R.id.main_drawerlayout)
        drawerList = findViewById(R.id.main_drawerlayout_list)
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
//            isDrawerIndicatorEnabled = false
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
        var data = ArrayList<DrawerItem>()
        for (i in 1..10) {
            data.add(DrawerItem("Item$i", 0))
        }
        val drawerItemAdapter =
            DrawerItemAdapter(baseContext, R.layout.main_drawerlayout_item, data)
        drawerList.adapter = drawerItemAdapter
    }
}

class DrawerItem(val content: String, val imgId: Int)

class DrawerItemAdapter(context: Context, val tvId: Int, val data: List<DrawerItem>) :
    ArrayAdapter<DrawerItem>(context, tvId, data) {
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
