package com.devd.common.ui.register.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.devd.common.R
import com.devd.common.theme.ColorBlackF8
import com.devd.common.theme.ColorDivider
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorTertiaryText
import com.devd.common.theme.ColorWhite
import com.devd.common.util.LabelText
import com.devd.common.util.RoundedCard

@Composable
fun InputStoreInfo(
    selectStoreIndex: Int,
    storeList: List<String>,
    memo: String,
    onStoreSelected: (Int) -> Unit,
    onMemoUpdate: (String) -> Unit,
    onAddMartList: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var isShowAddMartPopup by remember { mutableStateOf(false) }

    RoundedCard {
        LabelText(labelIcon = R.drawable.icon_shop, label = stringResource(R.string.purchase_store))

        FlowRow( // 가로로 배치하다 넘치면 아래로 내림
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            storeList.forEachIndexed { index, store ->
                FilterChip(
                    selected = index == selectStoreIndex,
                    onClick = { onStoreSelected(index) },
                    label = {
                        Text(
                            text = store,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ColorMainText, // 선택 시 어두운 색
                        selectedLabelColor = ColorWhite
                    )
                )
            }

            // 직접 입력 버튼
            AssistChip(
                onClick = { isShowAddMartPopup = true },
                label = {
                    Text(
                        text = stringResource(R.string.add_self),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = ColorTertiaryText
                        )
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = AssistChipDefaults.assistChipColors(containerColor = Color(0xFFF1F3F5))
            )
        }

        LabelText(labelIcon = R.drawable.icon_message, label = stringResource(R.string.option_memo))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorBlackF8, RoundedCornerShape(10.dp)),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = ColorMainText
            ),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            value = memo,
            onValueChange = { if (it.length <= 15) onMemoUpdate(it) },
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            placeholder = {
                Text(
                    text = stringResource(R.string.option_memo_placeholder),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = ColorTertiaryText
                    )
                )
            },
        )
    }
    if (isShowAddMartPopup) {
        SimpleInputDialog(
            onConfirm = {
                onAddMartList(it)
                isShowAddMartPopup = false
            },
            onDismiss = { isShowAddMartPopup = false },
            onClearFocus = { focusManager.clearFocus() }
        )
    }
}

@Preview
@Composable
fun SimpleInputDialog(
    onConfirm: (String) -> Unit = {},
    onDismiss: () -> Unit = {},
    onClearFocus: () -> Unit = {}
) {
    var martName by remember { mutableStateOf("") }
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .background(ColorWhite, RoundedCornerShape(20.dp))
                .fillMaxWidth()
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.add_purchase_store_question),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = ColorMainText
                )
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp)
                    .background(ColorBlackF8, RoundedCornerShape(10.dp)),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = ColorMainText
                ),
                shape = RoundedCornerShape(10.dp),
                maxLines = 1,
                singleLine = true,
                value = martName,
                keyboardActions = KeyboardActions(
                    onDone = { onClearFocus() }
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                onValueChange = {
                    if (it.length <= 10) martName = it
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.add_purchase_store_placeholder),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = ColorTertiaryText
                        )
                    )
                },
            )
            Spacer(Modifier.height(20.dp))
            HorizontalDivider(thickness = 1.dp, color = ColorDivider)
            Row(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                Text(
                    modifier = Modifier
                        .clickable(onClick = onDismiss)
                        .weight(1f)
                        .padding(vertical = 15.dp),
                    text = stringResource(R.string.cancel),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = ColorTertiaryText
                    )
                )
                VerticalDivider(
                    thickness = 1.dp,
                    color = ColorDivider
                )
                Text(
                    modifier = Modifier
                        .clickable(onClick = { onConfirm(martName) })
                        .weight(1f)
                        .padding(vertical = 15.dp),
                    text = stringResource(R.string.add_btn),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = ColorPrimaryBlue
                    )
                )
            }
        }
    }
}