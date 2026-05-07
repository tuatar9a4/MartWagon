package com.devd.home.record

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devd.common.R
import com.devd.common.theme.ColorBackground
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorWhite
import com.devd.common.ui.compare.PriceComparePopup
import com.devd.common.ui.register.PriceRegisterPopup
import com.devd.domain.model.database.PriceRecord
import com.devd.home.record.screen.RecordList
import com.devd.home.record.screen.RecordSummary
import com.devd.home.record.screen.TopBanner

sealed interface RecordAction {
    data object OnSearchClick : RecordAction
    data class OnCompareClick(val selectItem: PriceRecord) : RecordAction
}

@Composable
fun RecordScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var isShowRegisterPopup by remember { mutableStateOf(false) }
    var isShowComparePopup by remember { mutableStateOf<PriceRecord?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ColorBackground)
    ) {
        RecordScreen(
            uiState = uiState,
            homeActionListener = {
                when (it) {
                    RecordAction.OnSearchClick -> {
                        viewModel.tempDeleteAllItem()
                    }

                    is RecordAction.OnCompareClick -> {
                        isShowComparePopup = it.selectItem
                    }
                }
            }
        )
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 10.dp),
            containerColor = ColorPrimaryBlue,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 2.dp),
            onClick = { isShowRegisterPopup = true }
        ) {
            Image(
                painter = painterResource(R.drawable.icon_add),
                contentDescription = null,
                colorFilter = ColorFilter.tint(ColorWhite)
            )
        }
    }
    // 등록 팝업
    if (isShowRegisterPopup) {
        PriceRegisterPopup(
            onDismiss = { isShowRegisterPopup = false },
        )
    }

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
    homeActionListener: (RecordAction) -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBanner(
            onClickSearch = {
                homeActionListener(RecordAction.OnSearchClick)
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
                homeActionListener(RecordAction.OnCompareClick(selectItem))
            }
        )
    }

}

@Preview
@Composable
fun RecordScreenPreview() {
    RecordScreen(
        uiState = HomeUiState(),
        homeActionListener = {}
    )
}