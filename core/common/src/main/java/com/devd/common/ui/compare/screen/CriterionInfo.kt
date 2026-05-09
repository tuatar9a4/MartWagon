package com.devd.common.ui.compare.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.devd.common.theme.ColorBlackFA
import com.devd.common.theme.ColorDivider
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorRed
import com.devd.common.theme.ColorSecondaryText
import com.devd.common.theme.ColorTertiaryText
import com.devd.common.util.LabelText
import com.devd.common.util.RoundedCard
import com.devd.domain.model.database.PriceRecord
import com.devd.domain.model.database.PriceUnit

@Preview
@Composable
fun CriterionInfoPreview() {
    CriterionInfo(
        PriceRecord(
            id = 3199,
            productName = "Esther Roth",
            category = "",
            martName = "Robin Terry",
            currentPrice = 9524,
            originalPrice = 2576,
            memo = "equidem",
            recordDate = 2893,
            discountRate = 50,
            quantity = 100,
            unit = 1
        )
    )
}

@Preview
@Composable
fun CriterionInfoNullPreview() {
    CriterionInfo(
        PriceRecord(
            id = 3199,
            productName = "Esther Roth",
            category = "123",
            martName = "Robin Terry",
            currentPrice = 9524,
            originalPrice = null,
            memo = null,
            recordDate = 2893,
            discountRate = 50,
            quantity = 400,
            unit = 0
        )
    )
}

@Composable
fun CriterionInfo(
    priceRecord: PriceRecord
) {
    RoundedCard {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .background(ColorDivider, RoundedCornerShape(5.dp))
                    .padding(horizontal = 5.dp, vertical = 1.dp),
                text = stringResource(R.string.original_record),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 8.sp,
                    color = ColorMainText
                )
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = priceRecord.recordDateStr,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = ColorTertiaryText
                )
            )
        }
        Column() {
            LabelText(
                labelIcon = R.drawable.icon_shop,
                label = priceRecord.martName,
            )
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = priceRecord.productName,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = ColorMainText
                    )
                )
                priceRecord.quantity?.let {
                    Spacer(Modifier.width(3.dp))
                    Text(
                        text = "(${priceRecord.quantity}${stringResource(PriceUnit.entries[priceRecord.unit].display)})",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Medium,
                            color = ColorMainText
                        )
                    )
                }
            }
        }
        Column() {
            Row(verticalAlignment = Alignment.CenterVertically) {
                priceRecord.discountRate?.let {
                    Text(
                        text = "${priceRecord.discountRate}%",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = ColorRed
                        )
                    )
                    Spacer(Modifier.width(5.dp))
                }
                priceRecord.originalPriceStr?.let {
                    Text(
                        text = "${priceRecord.originalPriceStr} ${stringResource(R.string.currency_unit)}",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = ColorTertiaryText

                        )
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${priceRecord.currentPriceStr} ${stringResource(R.string.currency_unit)}",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = ColorMainText
                    )
                )
                priceRecord.unitPerPrice?.let {
                    Text(
                        text = "${priceRecord.unitPerStr}${stringResource(R.string.per_text)} " +
                                "${priceRecord.unitPerPrice} ${stringResource(R.string.currency_unit)}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = ColorPrimaryBlue
                        )
                    )
                }
            }
        }
        priceRecord.memo?.ifEmpty { null }?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, ColorDivider, RoundedCornerShape(5.dp))
                    .background(ColorBlackFA, RoundedCornerShape(5.dp))
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(12.dp),
                    painter = painterResource(R.drawable.icon_message),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(ColorPrimaryBlue)
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = ColorSecondaryText
                    )
                )
            }
        }
    }
}