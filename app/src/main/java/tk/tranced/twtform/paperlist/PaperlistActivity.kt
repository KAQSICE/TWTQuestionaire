package tk.tranced.twtform.paperlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import tk.tranced.twtform.R
import tk.tranced.twtform.data.Paper

class PaperlistActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.paperlist_activity)
        findViews()
        setToolbar()
    }

    private fun findViews() {
        toolbar = findViewById(R.id.commonToolbar)
        toolbarTitle = findViewById(R.id.commonToolbarTitle)
        recyclerView = findViewById(R.id.paperlistRecyclerView)
    }

    private fun setToolbar() {
        toolbar.title = ""
        //TODO:这里以后要分情况
        toolbarTitle.text = "我创建的"
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initPaperlistRecyclerView(paperlist: MutableList<Paper>) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@PaperlistActivity)
            withItems {

            }
        }
    }
}

//TODO:Work in progress
//TODO:要把paperlist的item布局写好
class PaperlistItem(val paper: Paper) : Item {
    companion object PaperlistItemController : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            TODO("Not yet implemented")
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.paperlist_item, parent, false)
            return PaperlistItemViewHolder(itemView)
        }
    }

    override val controller: ItemController
        get() = PaperlistItemController

    private class PaperlistItemViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView)
}