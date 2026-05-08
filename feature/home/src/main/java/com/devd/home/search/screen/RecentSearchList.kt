package com.devd.home.search.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorSecondaryText
import com.devd.common.theme.ColorTertiaryText

@Preview
@Composable
fun RecentSearchWordListPreview() {
    RecentSearchWordList(
        searchWordList = listOf("12,", "424", "마트", "우류"),
        onSearchWord = {},
        onDeleteSearchWord = {},
        onDeleteAllSearchWord = {}
    )
}


@Composable
fun RecentSearchWordList(
    searchWordList: List<String>,
    onSearchWord: (String) -> Unit,
    onDeleteSearchWord: (String) -> Unit,
    onDeleteAllSearchWord: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.recent_search_word_text),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = ColorSecondaryText
                )
            )
            Text(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { onDeleteAllSearchWord() }
                ),
                text = stringResource(R.string.all_delete),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = ColorTertiaryText
                )
            )
        }
        Spacer(Modifier.height(10.dp))
        LazyColumn() {
            items(searchWordList) { word ->
                Row(
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onSearchWord(word) }
                        )
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(R.drawable.icon_time_history),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(ColorTertiaryText)
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text = word,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = ColorMainText
                            )
                        )
                    }
                    Image(
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { onDeleteSearchWord(word) }
                            )
                            .size(30.dp)
                            .padding(7.dp),
                        painter = painterResource(R.drawable.icon_close),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(ColorTertiaryText)
                    )
                }
            }
        }
    }
}