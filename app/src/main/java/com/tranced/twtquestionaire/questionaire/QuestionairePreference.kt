package com.tranced.twtquestionaire.questionaire

import com.tranced.twtquestionaire.Question
import com.tranced.twtquestionaire.commons.experimental.preference.hawk

class QuestionairePreference {
    var questionaireEditorQuestion: Question? by hawk("q1_e_question", null)

    var questionaireEditorQuestionaire: Questionaire? by hawk("q1_e_questionaire", null)
}