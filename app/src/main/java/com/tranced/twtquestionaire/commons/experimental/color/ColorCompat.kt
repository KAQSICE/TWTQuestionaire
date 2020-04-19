package com.tranced.twtquestionaire.commons.experimental.color

import androidx.core.content.ContextCompat
import com.tranced.twtquestionaire.R
import com.tranced.twtquestionaire.commons.experimental.CommonContext
import io.multimoon.colorful.Colorful

/**
 * Created by retrox on 2018/3/26.
 */

fun getColorCompat(resId: Int): Int {
    return when (resId) {
        R.color.colorPrimary -> return Colorful().getPrimaryColor().getColorPack().normal().asInt()
        R.color.colorPrimaryDark -> return Colorful().getPrimaryColor().getColorPack().dark()
            .asInt()
        R.color.colorAccent -> return Colorful().getAccentColor().getColorPack().normal().asInt()
        else -> ContextCompat.getColor(CommonContext.application.applicationContext, resId)
    }
}