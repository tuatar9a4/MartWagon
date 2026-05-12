package com.devd.record.record

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devd.common.theme.ColorBackground
import com.devd.common.ui.compare.PriceComparePopup
import com.devd.common.ui.dialog.ShowMessageDialog
import com.devd.domain.model.database.PriceRecord
import com.devd.record.record.screen.RecordList
import com.devd.record.record.screen.RecordSummary
import com.devd.record.record.screen.TopBanner

sealed interface RecordAction {
    data object OnSettingClick : RecordAction
    data class OnCompareClick(val selectItem: PriceRecord) : RecordAction
    data class OnDeleteClick(val deleteId: Long) : RecordAction
}

@Composable
fun RecordScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    moveSettingPage: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var isShowComparePopup by remember { mutableStateOf<PriceRecord?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ColorBackground)
    ) {
        RecordScreen(
            uiState = uiState,
            recordActionListener = {
                when (it) {
                    RecordAction.OnSettingClick -> {
                        moveSettingPage()
                    }

                    is RecordAction.OnCompareClick -> {
                        isShowComparePopup = it.selectItem
                    }

                    is RecordAction.OnDeleteClick -> {
                        viewModel.askDeleteRecordItem(it.deleteId)
                    }
                }
            }
        )
    }

    //삭제 확인 팝업
    uiState.messageItem?.ShowMessageDialog()

    // 비교 팝업
    if (isShowComparePopup != null) {
        PriceComparePopup(
            criterionPrice = isShowComparePopup!!,
            onDismiss = { isShowComparePopup = null }
        )
    }
}

@Composable
fun RecordScreen(
    uiState: HomeUiState,
    recordActionListener: (RecordAction) -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBanner(
            settingClick = {
                recordActionListener(RecordAction.OnSettingClick)
            }
        )
        Spacer(Modifier.height(20.dp))
        RecordSummary(
            itemCount = uiState.recordItemCount,
            updateDate = uiState.recordLastTime
        )
        Spacer(Modifier.height(20.dp))
        RecordList(
            modifier = Modifier.weight(1f),
            priceRecords = uiState.recordItems,
            onClickFilter = {},
            onCompareClick = { selectItem ->
                recordActionListener(RecordAction.OnCompareClick(selectItem))
            },
            onDeleteClick = {
                recordActionListener(RecordAction.OnDeleteClick(it))
            }
        )
    }

}

@Preview
@Composable
fun RecordScreenPreview() {
    RecordScreen(
        uiState = HomeUiState(),
        recordActionListener = {}
    )
}