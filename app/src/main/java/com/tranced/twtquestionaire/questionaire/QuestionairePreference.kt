package com.tranced.twtquestionaire.questionaire

import com.tranced.twtquestionaire.Paper
import com.tranced.twtquestionaire.Question
import com.tranced.twtquestionaire.commons.experimental.preference.hawk

object QuestionairePreference {
    var q1Question: Question? by hawk("q1_e_question", null)

    var q1Paper: Paper? by hawk("q1_e_questionaire", null)
}