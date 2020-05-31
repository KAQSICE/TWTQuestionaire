package com.tranced.twtquestionaire.data

private val testPaperList = mutableListOf<Paper>()

fun getData() {
    for (i in 1..20) {
        testPaperList.add(
            Paper(
                "我是第$i 个Paper",
                when (i % 3) {
                    0 -> "问卷"
                    1 -> "投票"
                    else -> "答题"
                },
                "我只是一个Test",
                "测试学院",
                "测试专业",
                0,
                1,
                "测试作者",
                0,
                mutableListOf()
            )
        )
    }
    GlobalPreference.participatedPapers.apply {
        clear()
        addAll(testPaperList)
    }
}
