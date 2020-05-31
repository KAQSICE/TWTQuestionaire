package com.tranced.twtquestionaire.data

import com.tranced.twtquestionaire.commons.experimental.preference.hawk

object GlobalPreference {
    var user: User? by hawk("user", null)

    var question: Question? by hawk("q1_e_question", null)

    var q1Paper: Paper? by hawk("q1_e_paper", null)

    var vPaper: Paper? by hawk("v_e_paper", null)

    var q2Paper: Paper? by hawk("q2_e_paper", null)

    val createdPapers: MutableList<Paper> by hawk("created", mutableListOf())

    val participatedPapers: MutableList<Paper> by hawk("participated", mutableListOf())

    val starPapers: MutableList<Paper> by hawk("star", mutableListOf())
}