package com.devd.record.record.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devd.common.R
import com.devd.common.theme.ColorBlackDA
import com.devd.common.theme.ColorBlackFA
import com.devd.common.theme.ColorDeepDarkBlue
import com.devd.common.theme.ColorDivider
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorWhite
import com.devd.common.util.getDaysAgo

@Preview
@Composable
fun RecordSummaryPreview() {
    RecordSummary(
        itemCount = 0,
        updateDate = 0
    )
}

@Composable
fun RecordSummary(
    itemCount: Int,
    updateDate: Long?
) {
    val lastUpdateDay = updateDate?.let {
        val lastDay = it.getDaysAgo()
        if (lastDay == 0L) {
            stringResource(R.string.today_text)
        } else {
            "$lastDay ${stringResource(R.string.day_ago_text)}"
        }
    } ?: "-"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .background(ColorDeepDarkBlue, RoundedCornerShape(20.dp))
            .padding(15.dp)
    ) {

        Text(
            text = stringResource(R.string.my_recorder),
            style = MaterialTheme.typography.titleMedium.copy(
                color = ColorBlackDA
            )
        )
        Spacer(Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "$itemCount",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 30.sp,
                            color = ColorBlackFA,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = stringResource(R.string.item_text),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = ColorBlackDA,
                            lineHeight = 28.sp,
                            fontWeight = FontWeight.Normal

                        )
                    )
                }
                Spacer(Modifier.height(5.dp))
                Text(
                    text = stringResource(R.string.total_record_item),
                    style = MaterialTheme.typography.bodySmall.copy(ColorBlackDA)
                )
            }
            Column(
                modifier = Modifier
                    .border(1.dp, ColorDivider.copy(0.1f), RoundedCornerShape(10.dp))
                    .background(ColorWhite.copy(alpha = 0.1f), RoundedCornerShape(10.dp))
                    .padding(vertical = 10.dp, horizontal = 15.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = stringResource(R.string.update_text),
                    style = MaterialTheme.typography.labelMedium.copy(ColorBlackFA)
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    modifier = Modifier.widthIn(max = 80.dp),
                    textAlign = TextAlign.End,
                    text = lastUpdateDay,
                    autoSize = TextAutoSize.StepBased(
                        minFontSize = 7.sp,
                        maxFontSize = 14.sp
                    ),
                    style = MaterialTheme.typography.labelLarge.copy(ColorPrimaryBlue)
                )
            }
        }
    }
}