package com.devd.report.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorSecondaryText

@Composable
fun ReportTopBanner() {
    Column() {
        Text(
            text = "이번 달, 나의 물가 리포트",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                color = ColorMainText
            )
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "이번 달, 나의 물가 리포트 ",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = ColorSecondaryText
            )
        )
    }
}