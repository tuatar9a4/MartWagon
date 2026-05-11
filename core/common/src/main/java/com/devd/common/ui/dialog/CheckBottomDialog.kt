package com.devd.common.ui.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devd.common.R
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorPrimaryBlue

data class CheckItem(
    val id: String,
    val text: String,
    val isChecked: Boolean
)

@Preview
@Composable
fun CheckBottomDialogPreview() {
    CheckBottomDialog(
        checkItems = listOf(
            CheckItem(
                id = "1",
                text = "우유",
                isChecked = true
            ),
            CheckItem(
                id = "2",
                text = "우유12",
                isChecked = false
            )
        ),
        onDismissRequest = {},
        onItemClick = { _, _ -> }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckBottomDialog(
    checkItems: List<CheckItem>,
    onDismissRequest: () -> Unit,
    onItemClick: (index: Int, item: CheckItem) -> Unit
) {
    BasicBottomDialog(
        onDismissRequest = onDismissRequest
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "차트에 보여줄 제품을 선택해주세요.",
            style = MaterialTheme.typography.titleMedium.copy(
                color = ColorMainText,
                textAlign = TextAlign.Center
            )
        )
        LazyColumn() {
            itemsIndexed(checkItems) { index, item ->
                Row(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                onItemClick(index, item)
                            }
                        )
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.text,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = if (item.isChecked) ColorPrimaryBlue else ColorMainText
                        )
                    )
                    if (item.isChecked) Image(
                        painter = painterResource(R.drawable.icon_check),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(ColorPrimaryBlue)
                    )
                }
            }
        }
    }
}

