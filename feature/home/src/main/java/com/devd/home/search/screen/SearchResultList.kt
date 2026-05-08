package com.devd.home.search.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devd.common.R
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorTertiaryText
import com.devd.common.ui.compare.PriceComparePopup
import com.devd.common.ui.record.RecordPriceItem
import com.devd.common.ui.register.PriceRegisterPopup
import com.devd.common.util.LabelText
import com.devd.domain.model.database.PriceRecord

@Preview
@Composable
fun SearchResultListPreview() {
    SearchResultList(
        searchWord = "삼겹살",
        searchList = listOf()
    )
}

@Composable
fun SearchResultList(
    searchWord: String,
    searchList: List<PriceRecord>
) {
    var criterionItem by remember { mutableStateOf<PriceRecord?>(null) }
    var isShowAddItem by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        LabelText(
            labelIcon = R.drawable.icon_search,
            label = stringResource(R.string.search_result_message, searchWord, searchList.size)
        )
        if (searchList.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(100.dp))
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(R.drawable.icon_search),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(ColorTertiaryText)
                )
                Spacer(Modifier.height(15.dp))
                Text(
                    text = stringResource(R.string.empty_search_list),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = ColorTertiaryText
                    )
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    modifier = Modifier.clickable(onClick = { isShowAddItem = true }),
                    text = stringResource(R.string.ask_add_new_record),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = ColorPrimaryBlue,
                        textDecoration = TextDecoration.Underline
                    )
                )

            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(searchList) {
                    RecordPriceItem(
                        item = it,
                        onCompareClick = { criterionItem = it },
                        onDeleteItem = {}
                    )
                }
            }
        }
    }
    criterionItem?.let {
        PriceComparePopup(
            criterionPrice = it,
            onDismiss = { criterionItem = null }
        )
    }
    if (isShowAddItem) {
        PriceRegisterPopup(
            searchWord = searchWord
        ) {
            isShowAddItem = false
        }
    }
}