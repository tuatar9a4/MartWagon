package com.devd.common.ui.compare.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devd.common.R
import com.devd.common.theme.ColorBlackF8
import com.devd.common.theme.ColorDeepDarkBlue
import com.devd.common.theme.ColorDivider
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorSecondaryText
import com.devd.common.theme.ColorTertiaryText
import com.devd.common.theme.ColorWhite
import com.devd.common.ui.compare.model.CompareType
import com.devd.common.util.LabelText
import com.devd.common.util.RoundedCard

@Composable
fun PriceCompareResult(
    compareType: CompareType,
    onAddNewPrice: (String) -> Unit
) {
    var memo by remember { mutableStateOf("") }

    Column() {
        RoundedCard(
            containerColor = compareType.mainColor,
            borderColor = compareType.subColor
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(42.dp)
                        .background(ColorWhite, CircleShape)
                        .padding(5.dp),
                    painter = painterResource(compareType.icon),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(compareType.subColor)
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = stringResource(compareType.message),
                    maxLines = 1,
                    autoSize = TextAutoSize.StepBased(minFontSize = 8.sp, maxFontSize = 16.sp),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = compareType.subColor,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        RoundedCard(
            containerColor = ColorWhite,
            borderColor = ColorDivider
        ) {
            Column() {
                Text(
                    text = "이 가격으로 기록 갱신하기",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = ColorMainText
                    )
                )
                Text(
                    text = "(물가 변화 확인에 도움이 됩니다.)",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = ColorSecondaryText
                    )
                )
            }
            Column() {
                LabelText(labelIcon = R.drawable.icon_message, label = "메모(선택)")
                Spacer(Modifier.height(5.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ColorBlackF8, RoundedCornerShape(10.dp)),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = ColorMainText
                    ),
                    shape = RoundedCornerShape(10.dp),
                    value = memo,
                    onValueChange = { memo = it },
                    placeholder = {
                        Text(
                            text = "마감세일, 1+1 등",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = ColorTertiaryText
                            )
                        )
                    },
                )
            }
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ColorDeepDarkBlue, RoundedCornerShape(10.dp)),
                onClick = { onAddNewPrice(memo) }
            ) {
                Text(
                    text = "새로운 가격 추가",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = ColorWhite
                    )
                )
            }
        }
    }

}

@Preview
@Composable
fun PriceCompareScreen1Preview() {
    PriceCompareResult(
        compareType = CompareType.VERY_CHEAP,
        onAddNewPrice = {}
    )
}

@Preview
@Composable
fun PriceCompareScreen2Preview() {
    PriceCompareResult(
        compareType = CompareType.CHEAP,
        onAddNewPrice = {}
    )
}

@Preview
@Composable
fun PriceCompareScreen3Preview() {
    PriceCompareResult(
        compareType = CompareType.SAME,
        onAddNewPrice = {}
    )
}

@Preview
@Composable
fun PriceCompareScreen4Preview() {
    PriceCompareResult(
        compareType = CompareType.EXPENSIVE,
        onAddNewPrice = {}
    )
}