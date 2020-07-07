package tk.tranced.twtform.preference

import tk.tranced.twtform.data.Paper

object GlobalPreference {
    var paper: Paper? by hawk("paper", null)
}