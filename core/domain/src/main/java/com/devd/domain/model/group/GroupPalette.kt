package com.devd.domain.model.group

import androidx.compose.ui.graphics.Color
import com.devd.domain.model.datastore.SavedMartData

data class MartColorSet(val backgroundColor: Color, val contentColor: Color)

val GroupPalette = listOf(
    MartColorSet(Color(0xFFFFF9C4), Color(0xFFFBC02D)), // Yellow
    MartColorSet(Color(0xFFFFEBEE), Color(0xFFE57373)), // Red
    MartColorSet(Color(0xFFFCE4EC), Color(0xFFF06292)), // Pink
    MartColorSet(Color(0xFFE8F5E9), Color(0xFF81C784)), // Green
    MartColorSet(Color(0xFFE3F2FD), Color(0xFF64B5F6)), // Blue
    MartColorSet(Color(0xFFF3E5F5), Color(0xFFBA68C8)), // Purple
    MartColorSet(Color(0xFFF1F8E9), Color(0xFFAED581)), // Light Green
    MartColorSet(Color(0xFFFFFDE7), Color(0xFFFFD54F)), // Amber
    MartColorSet(Color(0xFFEFEBE9), Color(0xFFA1887F)), // Brown
    MartColorSet(Color(0xFFE0F2F1), Color(0xFF4DB6AC))  // Teal
)

val defaultMartList = listOf(
    SavedMartData(
        "이마트", 0
    ),
    SavedMartData(
        "홈플러스", 2
    ),
    SavedMartData(
        "코스트코", 1
    )
)