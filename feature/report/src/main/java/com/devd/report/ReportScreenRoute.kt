package com.devd.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devd.common.ui.dialog.CheckBottomDialog
import com.devd.common.ui.dialog.CheckItem
import com.devd.common.ui.dialog.ShowLoadingDialog
import com.devd.domain.model.report.ChartProductItem
import com.devd.domain.model.report.FluctuationInfo
import com.devd.domain.model.report.FluctuationReport
import com.devd.report.screen.CheapestMartRank
import com.devd.report.screen.DataAnalysing
import com.devd.report.screen.PriceChart
import com.devd.report.screen.PriceFluctuationInfo
import com.devd.report.screen.ReportTopBanner

sealed interface ReportAction {
    data class OnChangeProductOfChart(val index: Int, val productName: String) : ReportAction
    data object MoveSetting : ReportAction
}

@Composable
fun ReportScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: ReportViewModel = hiltViewModel(),
    onMoveSetting: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadFluctuationInfo()
    }

    ReportScreen(
        modifier = modifier,
        uiState = uiState,
        productNameList = viewModel.getProductNameList(),
        onAction = {
            when (it) {
                is ReportAction.OnChangeProductOfChart -> {
                    viewModel.changeChartProduct(it.index, it.productName)
                }

                ReportAction.MoveSetting -> onMoveSetting()
            }
        },
    )
    uiState.isLoading.ShowLoadingDialog { }
}

@Composable
fun ReportScreen(
    modifier: Modifier = Modifier,
    uiState: ReportUiState,
    productNameList: List<ChartProductItem>,
    onAction: (ReportAction) -> Unit,
) {
    var isShowProductList: Boolean by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ReportTopBanner(
            settingClick = { onAction(ReportAction.MoveSetting) }
        )
        Spacer(Modifier.height(20.dp))
        if (uiState.isContentEmpty()) {
            DataAnalysing(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        } else {
            uiState.fluctuationInfo?.let {
                PriceFluctuationInfo(
                    fluctuationReport = it
                )
            }
            Spacer(Modifier.height(20.dp))
            if (uiState.martComparisonList.size > 1) {
                CheapestMartRank(
                    rankingList = uiState.martComparisonList
                )
            }
            Spacer(Modifier.height(20.dp))
            if (uiState.priceList.size > 1) {
                PriceChart(
                    showProduct = uiState.priceList.first().productName,
                    priceList = uiState.priceList,
                    chartProductClick = {
                        isShowProductList = true
                    }
                )
            }
        }
        Spacer(Modifier.height(20.dp))
    }
    if (isShowProductList) {
        CheckBottomDialog(
            checkItems = productNameList.mapIndexed { index, item ->
                CheckItem(index.toString(), item.productName, item.isSelect)
            },
            onDismissRequest = {
                isShowProductList = false
            },
            onItemClick = { index, item ->
                onAction(ReportAction.OnChangeProductOfChart(index, item.text))
                isShowProductList = false
            }
        )
    }
}

@Preview
@Composable
fun ReportScreenPreview() {
    ReportScreen(
        uiState = ReportUiState(
            fluctuationInfo = FluctuationReport(
                surgeItems = FluctuationInfo(
                    itemName = "Nola Carpenter",
                    originalPrice = 2889,
                    currentPrice = 4338,
                    discountRate = 5140

                ), plungeItems = FluctuationInfo(
                    itemName = "Bennie Pearson",
                    originalPrice = 3746,
                    currentPrice = 5130,
                    discountRate = 8740

                )
            )
        ),
        productNameList = emptyList(),
        onAction = {}
    )
}

