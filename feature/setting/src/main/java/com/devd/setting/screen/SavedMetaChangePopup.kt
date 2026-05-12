package com.devd.setting.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devd.common.R
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorSecondaryText
import com.devd.common.theme.ColorSemiBlue
import com.devd.common.theme.ColorWhite
import com.devd.common.ui.dialog.CommonDialog
import com.devd.common.ui.textfield.BorderTextField

@Preview
@Composable
fun SavedMetaChangePopup(
    metaList: List<String> = listOf("123", "22", "52"),
    addMetaItem: (String) -> Unit = {},
    deleteMetaItem: (String) -> Unit = {},
    onClose: () -> Unit={},
) {
    var inputString by remember { mutableStateOf("") }

    CommonDialog(
        onRightText = R.string.close,
        onRightClick = onClose,
        onDismiss = onClose
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "마트 리스트 업데이트",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = ColorMainText
                )
            )
            Spacer(Modifier.height(20.dp))
            BorderTextField(
                value = inputString,
                onValueChange = { inputString = it },
                placeHolder = R.string.day_ago_text
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                metaList.filter {
                    if (inputString.isEmpty()) return@filter true
                    it.contains(inputString)
                }.forEach {
                    MetaItem(
                        itemName = it,
                        inputName = inputString,
                        deleteMetaItem = deleteMetaItem
                    )
                }
                if (inputString.isNotEmpty() && !metaList.contains(inputString)) {
                    AddMetaItem(
                        inputName = inputString,
                        addMetaItem = addMetaItem
                    )
                }

            }
        }
    }
}

@Composable
fun MetaItem(
    itemName: String,
    inputName: String,
    deleteMetaItem: (String) -> Unit
) {
    FilterChip(
        onClick = { }, // 칩 클릭 시 텍스트 필드에 입력됨
        label = {
            Text(
                text = itemName,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                )
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = ColorWhite,
            selectedContainerColor = ColorMainText, // 선택 시 어두운 색
            selectedLabelColor = ColorWhite
        ),
        trailingIcon = {
            Image(
                modifier = Modifier
                    .clickable(onClick = { deleteMetaItem(itemName) })
                    .size(16.dp),
                painter = painterResource(R.drawable.icon_close),
                contentDescription = null,
                colorFilter = ColorFilter.tint(ColorSecondaryText)
            )
        },
        selected = itemName == inputName,
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun AddMetaItem(
    inputName: String,
    addMetaItem: (String) -> Unit
) {
    SuggestionChip(
        onClick = { addMetaItem(inputName) },
        label = {
            Text(
                text = "+ $inputName 추가하기",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = ColorPrimaryBlue
                )
            )
        },
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = ColorWhite
        ),
        border = SuggestionChipDefaults.suggestionChipBorder(
            borderColor = ColorSemiBlue,
            borderWidth = 1.dp,
            enabled = true
        ),
        shape = RoundedCornerShape(8.dp)
    )
}