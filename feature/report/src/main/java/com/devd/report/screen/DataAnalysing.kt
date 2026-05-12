package com.devd.report.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devd.common.R
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorSecondaryText
import com.devd.common.theme.ColorTertiaryText

@Preview
@Composable
fun DataAnalysing(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier.size(56.dp),
            painter = painterResource(R.drawable.icon_bar_chart),
            contentDescription = null,
            colorFilter = ColorFilter.tint(ColorTertiaryText)
        )
        Spacer(Modifier.height(20.dp))
        Text(
            text = "데이터 분석 준비중",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = ColorMainText,
                textAlign = TextAlign.Center
            )
        )
        Spacer(Modifier.height(20.dp))
        Text(
            text = "분석하기 충분한 양의 제품이 쌓이지 않았습니다.\n더 많은 제품 데이터를 입력해주세요.\n(최소 2개 이상의 다른 마트,2개 이상의 다른 가격이 기록되어 있어야 합니다.)",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = ColorSecondaryText,
                textAlign = TextAlign.Center
            )
        )

    }
}