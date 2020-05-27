package com.tranced.twtquestionaire

import com.tranced.twtquestionaire.commons.experimental.preference.hawk

object GlobalPreference {
    var q1Question: Question? by hawk("q1_e_question", null)

    var q1Paper: Paper? by hawk("q1_e_questionaire", null)

    var vQuestion: Question? by hawk("v_e_question", null)

    var vPaper: Paper? by hawk("v_e_question", null)
}