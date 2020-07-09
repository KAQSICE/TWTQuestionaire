package tk.tranced.twtform.preference

import tk.tranced.twtform.data.Paper

object GlobalPreference {
    var paper: Paper? by hawk("paper", null)

    //这个是我创建的问卷
    var createdPaperList: MutableList<Paper> by hawk("createdPaperList", mutableListOf())
}