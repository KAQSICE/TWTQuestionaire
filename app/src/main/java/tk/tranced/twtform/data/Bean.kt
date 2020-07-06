package tk.tranced.twtform.data

data class Paper(
    var id: Int,
    var name: String,
    var type: Int,
    var description: String?,
    var startTime: Long?,
    var endTime: Long?,
    var questions: MutableList<Question>,
    var random: Int,
    var times: Int
)

data class Question(
    var content: String,
    var type: Int,
    var point: Int,
    val correctAnswer: MutableList<Int>,    //TODO:正在商量，不要着急嘛
    var options: MutableList<String>,
    var random: Int,
    var necessary: Int
)

data class User(
    var username: String,
    var profession: String,
    var faculty: String
)