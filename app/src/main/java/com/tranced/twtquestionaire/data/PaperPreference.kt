package com.tranced.twtquestionaire.data

import com.tranced.twtquestionaire.Paper
import com.tranced.twtquestionaire.commons.experimental.preference.hawk

object PaperPreference {
    val paperList: MutableList<Paper> by hawk("created_paper_list", mutableListOf())
}