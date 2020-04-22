package com.tranced.twtquestionaire.questionaire

import java.util.*

class Questionaire(
    var title: String,
    var description: String?,
    var beginDate: Date?,
    var endDate: Date?
) {
    val itemList: MutableList<QuestionaireItem> = mutableListOf()
}

abstract class QuestionaireItem