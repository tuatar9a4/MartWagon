package com.devd.tag.group.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import com.devd.common.util.RoundedCard
import com.devd.domain.model.database.PriceRecord
import com.devd.domain.model.datastore.SavedMartData
import com.devd.domain.model.group.GroupPalette

@Preview
@Composable
fun MartGroupListPreview() {
    val groupList = listOf(
        SavedMartData("마트1", 0),
        SavedMartData("마트2", 1),
        SavedMartData("마트3", 2),
        SavedMartData("마트4", 3),
    )
    MartGroupList(
        groupList = groupList,
        itemsMap = mapOf(
            "마트2" to listOf(
                PriceRecord(
                    id = 9582,
                    productName = "Elijah Davenport",
                    martName = "Ira Hurley",
                    currentPrice = 4767,
                    originalPrice = 2055,
                    memo = "omittam",
                    recordDate = 7131,
                    quantity = 9309,
                    unit = 1858,
                    category = "minim",
                    discountRate = 3145
                )
            )
        ), {}
    )
}

@Composable
fun MartGroupList(
    groupList: List<SavedMartData>,
    itemsMap: Map<String, List<PriceRecord>>,
    onItemClick: (String) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(groupList) { item ->
            MartItem(
                martData = item,
                itemCount = itemsMap.getOrDefault(item.martName, emptyList()).size,
                itemClick = { onItemClick(item.martName) }
            )
        }
    }
}

@Composable
fun MartItem(
    martData: SavedMartData,
    itemCount: Int,
    itemClick: () -> Unit
) {
    val itemColor = GroupPalette[martData.colorPallet]
    RoundedCard(
        modifier = Modifier
            .clickable(onClick = itemClick)
            .aspectRatio(1 / 1f),
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.CenterHorizontally)
                .background(itemColor.backgroundColor, RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.icon_shop),
                contentDescription = null,
                colorFilter = ColorFilter.tint(itemColor.contentColor)
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = martData.martName,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = ColorMainText
                )
            )
            Spacer(Modifier.height(5.dp))
            Text(
                modifier = Modifier
                    .background(ColorDisable, RoundedCornerShape(40.dp))
                    .padding(vertical = 3.dp, horizontal = 5.dp),
                text = "$itemCount 개 기록",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = ColorMainText
                )
            )
        }

    }
}