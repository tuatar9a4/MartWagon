package com.devd.report.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devd.common.R
import com.devd.common.theme.ColorDisable
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.util.RoundedCard
import com.devd.domain.model.report.SimplePriceInfo
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.compose.cartesian.data.lineSeries
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import java.time.Instant
import java.time.ZoneId

@Preview
@Composable
fun PriceChartPreview() {
    PriceChart(
        showProduct = "서울 우유",
        priceList = listOf(
            SimplePriceInfo(
                productName = "Eugenia Wong", price = 8811, date = System.currentTimeMillis()
            ),
            SimplePriceInfo(
                productName = "Eugenia Wong",
                price = 8000,
                date = System.currentTimeMillis() - (24 * 60 * 60 * 1000)
            ),
            SimplePriceInfo(
                productName = "Eugenia Wong",
                price = 7200,
                date = System.currentTimeMillis() - (2 * 24 * 60 * 60 * 1000)
            ),
            SimplePriceInfo(
                productName = "Eugenia Wong",
                price = 4288,
                date = System.currentTimeMillis() - (3 * 24 * 60 * 60 * 1000)
            ),
        ),
        chartProductClick = {}
    )
}

@Composable
fun PriceChart(
    showProduct: String,
    priceList: List<SimplePriceInfo>,
    chartProductClick: () -> Unit = {}
) {

    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(priceList) {
        val xList = mutableListOf<Long>()
        val yList = mutableListOf<Long>()
        priceList.forEach {
            xList.add(it.date)
            yList.add(it.price)
        }
        modelProducer.runTransaction {
            lineSeries {
                series(x = xList, y = yList)
            }
        }
    }
    RoundedCard() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_bar_chart),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(ColorPrimaryBlue)
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = stringResource(R.string.price_chart_title),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = ColorMainText
                    )
                )
            }
            Row(
                modifier = Modifier
                    .clickable(onClick = chartProductClick)
                    .background(ColorDisable, RoundedCornerShape(5.dp))
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = showProduct,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = ColorMainText
                    )
                )
                Image(
                    painter = painterResource(R.drawable.icon_drop_down),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(ColorMainText)
                )
            }
        }
        CartesianChartHost(
            modifier = Modifier.fillMaxWidth(),
            chart = rememberCartesianChart(
                rememberLineCartesianLayer(),
                startAxis = VerticalAxis.rememberStart(line = null),
                bottomAxis = HorizontalAxis.rememberBottom(
                    valueFormatter = remember {
                        CartesianValueFormatter { _, value, _ ->
                            val instant = Instant.ofEpochMilli(value.toLong())
                            val dateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
                            "${dateTime.monthValue}.${dateTime.dayOfMonth}"
                        }
                    }
                )
            ),
            modelProducer = modelProducer
        )
    }
}