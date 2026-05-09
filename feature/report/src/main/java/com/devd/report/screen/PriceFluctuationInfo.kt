package com.devd.report.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devd.common.R
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorRed
import com.devd.common.theme.ColorSemiBlue
import com.devd.common.theme.ColorSemiRed
import com.devd.common.theme.ColorTertiaryText
import com.devd.common.util.RoundedCard
import com.devd.domain.model.report.FluctuationInfo
import com.devd.domain.model.report.FluctuationReport
import java.text.DecimalFormat
import kotlin.math.abs

@Preview
@Composable
fun PriceFluctuationInfoPreview() {
    PriceFluctuationInfo(
        fluctuationReport = FluctuationReport(
            FluctuationInfo(
                itemName = "삼다수 2L",
                originalPrice = 1000,
                currentPrice = 200,
                discountRate = 10
            ),
            FluctuationInfo(
                itemName = "삼다수 2L",
                originalPrice = 1000,
                currentPrice = 800,
                discountRate = 10
            )
        )
    )
}

@Composable
fun PriceFluctuationInfo(
    fluctuationReport: FluctuationReport,
) {
    val formatter = DecimalFormat("#,###")

    val surgeItemInfo = fluctuationReport.surgeItems
    val plungeItemInfo = fluctuationReport.plungeItems

    RoundedCard() {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.icon_alerrt),
                contentDescription = null,
                colorFilter = ColorFilter.tint(ColorRed)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = stringResource(R.string.price_fluctuation_title),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = ColorMainText
                )
            )
        }
        Spacer(Modifier)
        RoundedCard(
            containerColor = ColorSemiRed,
            borderColor = ColorSemiRed
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.fluctuation_top_up),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ColorRed
                    )
                )
                Row() {
                    Image(
                        painter = painterResource(R.drawable.icon_chart_up),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(ColorRed)
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = "+ ${formatter.format(abs(surgeItemInfo.originalPrice - surgeItemInfo.currentPrice))}" +
                                stringResource(R.string.currency_unit),
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = ColorRed
                        )
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = surgeItemInfo.itemName,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = ColorMainText
                    )
                )
                Text(
                    text = stringResource(
                        R.string.original_price_with_currency,
                        formatter.format(surgeItemInfo.originalPrice)
                    ),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = ColorTertiaryText,
                        textDecoration = TextDecoration.LineThrough
                    )
                )
            }
        }
        RoundedCard(
            containerColor = ColorSemiBlue,
            borderColor = ColorSemiBlue
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.fluctuation_top_down),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ColorPrimaryBlue
                    )
                )
                Row() {
                    Image(
                        painter = painterResource(R.drawable.icon_chart_down),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(ColorPrimaryBlue)
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = "- ${formatter.format(abs(plungeItemInfo.originalPrice - plungeItemInfo.currentPrice))}" +
                                stringResource(R.string.currency_unit),
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = ColorPrimaryBlue
                        )
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = plungeItemInfo.itemName,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = ColorMainText
                    )
                )
                Text(
                    text = stringResource(
                        R.string.original_price_with_currency,
                        formatter.format(plungeItemInfo.originalPrice)
                    ),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = ColorTertiaryText,
                        textDecoration = TextDecoration.LineThrough
                    )
                )
            }
        }
    }
}