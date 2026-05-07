package com.devd.common.ui.compare.model

import androidx.compose.ui.graphics.Color
import com.devd.common.R
import com.devd.common.theme.ColorBlackFA
import com.devd.common.theme.ColorGreen
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorRed
import com.devd.common.theme.ColorSecondaryText
import com.devd.common.theme.ColorSemiBlue
import com.devd.common.theme.ColorSemiGreen
import com.devd.common.theme.ColorSemiRed

enum class CompareType(
    val message: Int,
    val icon: Int,
    val mainColor: Color,
    val subColor: Color
) {
    VERY_CHEAP(
        message = R.string.very_cheap_price_message,
        icon = R.drawable.icon_chart_down,
        mainColor = ColorSemiGreen,
        subColor = ColorGreen
    ),
    CHEAP(
        message = R.string.cheap_price_message,
        icon = R.drawable.icon_chart_down,
        mainColor = ColorSemiBlue,
        subColor = ColorPrimaryBlue
    ),
    SAME(
        message = R.string.same_price_message,
        icon = R.drawable.icon_chart_flat,
        mainColor = ColorBlackFA,
        subColor = ColorSecondaryText
    ),
    EXPENSIVE(
        message = R.string.expensive_price_message,
        icon = R.drawable.icon_chart_up,
        mainColor = ColorSemiRed,
        subColor = ColorRed
    )
}