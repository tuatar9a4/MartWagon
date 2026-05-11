package com.devd.tag.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devd.common.R
import com.devd.common.theme.ColorBackground
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorSecondaryText
import com.devd.common.theme.ColorWhite
import com.devd.common.ui.compare.PriceComparePopup
import com.devd.common.ui.dialog.ShowMessageDialog
import com.devd.common.ui.record.RecordPriceItem
import com.devd.domain.model.database.PriceRecord
import com.devd.domain.model.group.GroupType

sealed interface DetailAction {
    data object BackPage : DetailAction
    data class ShowCompareDialog(val criterion: PriceRecord) : DetailAction
    data class AskDeleteItem(val deleteId: Long) : DetailAction
}

@Composable
fun DetailScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    detailType: GroupType,
    itemName: String,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var criterionItem: PriceRecord? by remember { mutableStateOf(null) }


    LaunchedEffect(Unit) {
        viewModel.getDetailList(detailType, itemName)
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        DetailScreen(
            detailType = detailType,
            itemName = itemName,
            uiState = uiState,
            onAction = {
                when (it) {
                    DetailAction.BackPage -> onBackClick()
                    is DetailAction.ShowCompareDialog -> {
                        criterionItem = it.criterion
                    }

                    is DetailAction.AskDeleteItem -> viewModel.askDeleteItem(it.deleteId)
                }
            }
        )
    }

    criterionItem?.let {
        PriceComparePopup(
            criterionPrice = criterionItem!!,
            onDismiss = { criterionItem = null }
        )
    }

    uiState.messageItem?.ShowMessageDialog()
}


@Composable
fun DetailScreen(
    detailType: GroupType,
    itemName: String,
    uiState: DetailUiState,
    onAction: (DetailAction) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorWhite)
                .padding(vertical = 15.dp, horizontal = 20.dp)
        ) {
            Image(
                modifier = Modifier.clickable(
                    onClick = { onAction(DetailAction.BackPage) }
                ),
                painter = painterResource(R.drawable.icon_left_arrow),
                contentDescription = null,
                colorFilter = ColorFilter.tint(ColorMainText)
            )
            Spacer(Modifier.width(20.dp))
            Image(
                painter = painterResource(
                    if (detailType == GroupType.MART) R.drawable.icon_shop
                    else R.drawable.icon_hash_tag
                ),
                contentDescription = null,
                colorFilter = ColorFilter.tint(ColorPrimaryBlue)
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = "$itemName 기록",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = ColorMainText
                )
            )
        }
        HorizontalDivider(Modifier.fillMaxWidth(), 1.dp, ColorSecondaryText)
        Spacer(Modifier.height(25.dp))
        if (uiState.detailList.isEmpty()) {
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = "기록이 존재 하지 않습니다.",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = ColorMainText
                )
            )
        } else {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "총 ${uiState.detailList.size}개의 상품",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = ColorSecondaryText
                    )
                )
                Spacer(Modifier.height(15.dp))
                LazyColumn(
                    modifier = Modifier
                        .weight(1f),
                    state = rememberLazyListState()
                ) {
                    items(uiState.detailList) { item ->
                        RecordPriceItem(
                            item = item,
                            onCompareClick = { onAction(DetailAction.ShowCompareDialog(item)) },
                            onDeleteItem = { onAction(DetailAction.AskDeleteItem(item.id)) }
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(20.dp))
    }
}