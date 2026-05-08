package com.devd.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devd.domain.model.report.FluctuationInfo
import com.devd.domain.model.report.FluctuationReport
import com.devd.report.screen.PriceFluctuationInfo
import com.devd.report.screen.ReportTopBanner

@Composable
fun ReportScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: ReportViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadFluctuationInfo()
    }

    ReportScreen(
        modifier = modifier,
        uiState = uiState
    )
}

@Composable
fun ReportScreen(
    modifier: Modifier = Modifier,
    uiState: ReportUiState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(20.dp))
        ReportTopBanner()
        Spacer(Modifier.height(20.dp))
        uiState.fluctuationInfo?.let {
            PriceFluctuationInfo(
                fluctuationReport = it
            )
        }
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
        )
    )
}

