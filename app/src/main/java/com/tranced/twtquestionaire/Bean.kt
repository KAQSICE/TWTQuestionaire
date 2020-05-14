package com.tranced.twtquestionaire

data class Paper(
    var title: String,
    var type: String,
    var description: String?,
    var faculty: String,
    var profession: String,
    var power: Int,
    var lastTime: Int,
    var creator: String,
    var questionCount: Int,
    var questions: MutableList<Question>
)

data class Question(
    var stem: String,
    var type: String,
    var point: Int,
    var correctAnwser: String,
    var optionsCount: Int,
    var options: Options //TODO:填空题怎么搞
)

data class Options(
    var questionId: Int,
    val options: MutableList<Option>
)

data class Option(
    var name: String,
    var id: Int,
    var content: String
)