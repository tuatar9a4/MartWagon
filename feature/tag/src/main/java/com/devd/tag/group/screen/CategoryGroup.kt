package com.devd.tag.group.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devd.common.R
import com.devd.common.theme.ColorDisable
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorSemiBlue
import com.devd.common.theme.ColorTertiaryText
import com.devd.common.theme.ColorWhite
import com.devd.domain.model.database.PriceRecord

@Preview
@Composable
fun CategoryGroupPreview() {
    Column() {
        Spacer(Modifier.height(100.dp))
        CategoryGroup(
            categoryList = listOf("과자", "콜라", "만두"),
            itemsMap = mapOf(), {}
        )

    }
}

@Composable
fun CategoryGroup(
    categoryList: List<String>,
    itemsMap: Map<String, List<PriceRecord>>,
    onItemClick: (String) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categoryList.forEach { item ->
            CategoryItem(
                category = item,
                itemCount = itemsMap.getOrDefault(item, emptyList()).size,
                itemClick = { onItemClick(item) }
            )
        }
    }
}

@Composable
fun CategoryItem(
    category: String,
    itemCount: Int,
    itemClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable(onClick = itemClick),
        shape = RoundedCornerShape(15.dp),
        color = ColorWhite,
        border = BorderStroke(1.dp, ColorSemiBlue),
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(18.dp),
                painter = painterResource(R.drawable.icon_hash_tag),
                contentDescription = null,
                colorFilter = ColorFilter.tint(ColorPrimaryBlue)
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = category,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = ColorMainText
                )
            )
            Spacer(Modifier.width(10.dp))
            Text(
                modifier = Modifier
                    .background(ColorDisable, RoundedCornerShape(5.dp))
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                text = "$itemCount",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = ColorTertiaryText
                )
            )
        }
    }
}