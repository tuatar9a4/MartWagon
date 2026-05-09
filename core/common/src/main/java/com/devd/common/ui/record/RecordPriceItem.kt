package com.devd.common.ui.record

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devd.common.R
import com.devd.common.theme.ColorDivider
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorRed
import com.devd.common.theme.ColorSecondaryText
import com.devd.common.theme.ColorSemiBlue
import com.devd.common.theme.ColorTertiaryText
import com.devd.domain.model.database.PriceRecord

@Preview
@Composable
fun RecordPriceItemPreview() {
    RecordPriceItem(
        item = PriceRecord(
            id = 8012,
            productName = "Kendra Kennedy",
            martName = "Mercedes Dillard",
            currentPrice = 4039,
            originalPrice = 3582,
            category = "",
            memo = "expetendis",
            recordDate = 2969,
            discountRate = 20,
            quantity = 6000,
            unit = 1
        ),
        onCompareClick = {},
        onDeleteItem = {}
    )
}

@Composable
fun RecordPriceItem(
    item: PriceRecord,
    onCompareClick: (PriceRecord) -> Unit,
    onDeleteItem: (id: Long) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {},
                onLongClick = { onDeleteItem(item.id) }
            ),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        tonalElevation = 1.dp,
        shadowElevation = 1.dp,
        border = BorderStroke(1.dp, ColorDivider.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(15.dp),
                        painter = painterResource(R.drawable.icon_tag),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(ColorSecondaryText)
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        modifier = Modifier
                            .background(ColorDivider, RoundedCornerShape(5.dp))
                            .padding(horizontal = 5.dp, vertical = 3.dp),
                        text = item.martName,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 11.sp,
                            color = ColorSecondaryText
                        )
                    )
                    Spacer(Modifier.width(5.dp))
                    item.memo?.ifEmpty { null }?.let {
                        Row(
                            modifier = Modifier
                                .background(ColorSemiBlue, RoundedCornerShape(5.dp))
                                .padding(horizontal = 5.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(13.dp),
                                painter = painterResource(R.drawable.icon_message),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(ColorPrimaryBlue)
                            )
                            Spacer(Modifier.width(5.dp))
                            Text(
                                text = it,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontSize = 11.sp,
                                    color = ColorPrimaryBlue
                                )
                            )
                        }
                    }
                }
                Text(
                    text = item.recordDateStr,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = ColorTertiaryText
                    )
                )
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = item.productName,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = ColorMainText
                )
            )
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column() {
                        item.originalPriceStr?.let {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${item.discountRate}%",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ColorRed,
                                        lineHeight = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(Modifier.width(5.dp))
                                Text(
                                    text = "$it 원",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ColorTertiaryText,
                                        lineHeight = 11.sp,
                                    ),
                                    textDecoration = TextDecoration.LineThrough
                                )
                            }
                        }
                        Text(
                            text = "${item.currentPriceStr} 원",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = ColorMainText,
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false // 시스템 기본 패딩 제거
                                ),
                                lineHeightStyle = LineHeightStyle(
                                    alignment = LineHeightStyle.Alignment.Center, // 텍스트를 줄 높이 중앙에 배치
                                    trim = LineHeightStyle.Trim.Both // 첫 줄 상단과 마지막 줄 하단 여백 제거
                                )
                            )
                        )
                        item.unitPerPrice?.let {
                            Text(
                                text = "${item.unitPerStr}${stringResource(R.string.per_text)}" +
                                        " ${item.unitPerPrice}${stringResource(R.string.currency_unit)}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ColorPrimaryBlue
                                )
                            )
                        }

                    }
                }
                Row(
                    modifier = Modifier
                        .background(ColorSemiBlue, RoundedCornerShape(15.dp))
                        .clickable(onClick = { onCompareClick(item) })
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(R.drawable.icon_balance),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(ColorPrimaryBlue)
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = "비교하기",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = ColorPrimaryBlue
                        )
                    )
                }

            }
        }
    }
}