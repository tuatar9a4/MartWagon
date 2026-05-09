package com.devd.report.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devd.common.R
import com.devd.common.theme.ColorDeepDarkBlue
import com.devd.common.theme.ColorDivider
import com.devd.common.theme.ColorSecondaryText
import com.devd.common.theme.ColorSemiYellow
import com.devd.common.theme.ColorWhite
import com.devd.common.theme.ColorYellow
import com.devd.common.util.RoundedCard
import com.devd.domain.model.report.CategoryReport

@Preview
@Composable
fun MartComparisonPreview() {
    val reportList = listOf<Pair<String, List<CategoryReport>>>()
    CheapestMartRank(
        rankingList = listOf(
            "이마트" to listOf(
                CategoryReport(
                    categoryName = "Noemi Petty",
                    cheapestMartName = "Robby Miranda",
                    cheapestPrice = 6473,
                    cheapestPriceForUnit = "vehicula",
                    savingAmount = 7799
                ),
                CategoryReport(
                    categoryName = "Noemi Petty",
                    cheapestMartName = "Robby Miranda",
                    cheapestPrice = 6473,
                    cheapestPriceForUnit = "vehicula",
                    savingAmount = 7799

                ),
                CategoryReport(
                    categoryName = "Noemi Petty",
                    cheapestMartName = "Robby Miranda",
                    cheapestPrice = 6473,
                    cheapestPriceForUnit = "vehicula",
                    savingAmount = 7799

                ),
                CategoryReport(
                    categoryName = "Noemi Petty",
                    cheapestMartName = "Robby Miranda",
                    cheapestPrice = 6473,
                    cheapestPriceForUnit = "vehicula",
                    savingAmount = 7799

                )
            ),
            "홈플러스" to listOf(
                CategoryReport(
                    categoryName = "Noemi Petty",
                    cheapestMartName = "Robby Miranda",
                    cheapestPrice = 6473,
                    cheapestPriceForUnit = "vehicula",
                    savingAmount = 7799
                ),
                CategoryReport(
                    categoryName = "Noemi Petty",
                    cheapestMartName = "Robby Miranda",
                    cheapestPrice = 6473,
                    cheapestPriceForUnit = "vehicula",
                    savingAmount = 7799
                ),
                CategoryReport(
                    categoryName = "Noemi Petty",
                    cheapestMartName = "Robby Miranda",
                    cheapestPrice = 6473,
                    cheapestPriceForUnit = "vehicula",
                    savingAmount = 7799
                ),

                ),
            "코스트코" to listOf(
                CategoryReport(
                    categoryName = "Noemi Petty",
                    cheapestMartName = "Robby Miranda",
                    cheapestPrice = 6473,
                    cheapestPriceForUnit = "vehicula",
                    savingAmount = 7799
                ),
                CategoryReport(
                    categoryName = "Noemi Petty",
                    cheapestMartName = "Robby Miranda",
                    cheapestPrice = 6473,
                    cheapestPriceForUnit = "vehicula",
                    savingAmount = 7799
                )
            )
        )
    )
}

@Composable
fun CheapestMartRank(
    rankingList: List<Pair<String, List<CategoryReport>>> = listOf()
) {
    val totalSize = rankingList.sumOf { it.second.size }

    RoundedCard(
        containerColor = ColorDeepDarkBlue,
        borderColor = ColorDeepDarkBlue
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.icon_trophy),
                contentDescription = null,
                colorFilter = ColorFilter.tint(ColorYellow)
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = stringResource(R.string.ask_cheapest_mart),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(5.sp, 20.sp),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 20.sp,
                    color = ColorWhite
                )
            )
        }
        HorizontalDivider(thickness = 1.dp, color = ColorDivider)
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            (0..1).forEach {
                val (martName, rankItem) = rankingList[it]
                RankItem(
                    isFirst = it == 0,
                    martName = martName,
                    itemCount = rankItem.size,
                    totalSize = totalSize
                )
            }
        }
    }

}

@Composable
fun RankItem(
    isFirst: Boolean,
    martName: String,
    itemCount: Int,
    totalSize: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(45.dp)
                .background(
                    if (isFirst) ColorSemiYellow else ColorSecondaryText.copy(alpha = 0.4f),
                    CircleShape
                )
                .border(
                    1.dp,
                    if (isFirst) ColorYellow else ColorSecondaryText.copy(alpha = 0.4f),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isFirst) "1" else "2",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (isFirst) ColorYellow else ColorSecondaryText
                )
            )
        }
        Spacer(Modifier.width(20.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = martName,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (isFirst) ColorWhite else ColorSecondaryText
                    )
                )
                Text(
                    text = stringResource(R.string.cheapest_count, itemCount),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (isFirst) ColorYellow else ColorSecondaryText
                    )
                )
            }
            Spacer(Modifier.height(5.dp))
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
                color = if (isFirst) ColorYellow else ColorSecondaryText,
                trackColor = ColorSecondaryText.copy(alpha = 0.4f),
                gapSize = (-10).dp,
                drawStopIndicator = { },
                progress = { itemCount / totalSize.toFloat() }
            )
        }

    }
}