package com.devd.record.record.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devd.common.R
import com.devd.common.theme.ColorDivider
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorTertiaryText
import com.devd.common.ui.record.RecordPriceItem
import com.devd.domain.model.database.PriceRecord

@Preview
@Composable
fun RecordListPreview() {
    RecordList(
        onClickFilter = {},
        onCompareClick = {},
        onDeleteClick = {}
    )
}

@Composable
fun RecordList(
    modifier: Modifier = Modifier,
    priceRecords: List<PriceRecord> = emptyList(),
    onClickFilter: () -> Unit,
    onCompareClick: (PriceRecord) -> Unit,
    onDeleteClick :(Long) -> Unit
) {

    Column(
        modifier = modifier.then(
            Modifier.padding(horizontal = 20.dp)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.width(16.dp),
                    painter = painterResource(R.drawable.icon_time),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(ColorPrimaryBlue)
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = stringResource(R.string.recent_record_price),
                    style = MaterialTheme.typography.titleMedium.copy(ColorMainText)
                )
            }
            Text(
                modifier = Modifier
                    .background(ColorDivider, RoundedCornerShape(40.dp))
                    .clickable(onClick = onClickFilter)
                    .padding(horizontal = 10.dp, vertical = 3.dp),
                text = stringResource(R.string.recent),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 11.sp,
                    color = ColorTertiaryText
                )
            )
        }
        LazyColumn(
            contentPadding = PaddingValues(vertical = 15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(priceRecords) { item ->
                RecordPriceItem(
                    item = item,
                    onCompareClick = onCompareClick,
                    onDeleteItem = onDeleteClick
                )
            }
        }
    }
}
