package tk.tranced.twtform.preference

import tk.tranced.twtform.data.Paper
import tk.tranced.twtform.data.Question

object GlobalPreference {
    var paper: Paper? by hawk("paper", null)

    val questionList: MutableList<Question> by hawk("questionList", mutableListOf())
}