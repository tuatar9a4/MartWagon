package com.devd.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devd.domain.model.report.CategoryReport
import com.devd.domain.model.report.ChartProductItem
import com.devd.domain.model.report.FluctuationReport
import com.devd.domain.model.report.RangePeriod
import com.devd.domain.model.report.SimplePriceInfo
import com.devd.domain.usecase.record.FetchProductNameListUseCase
import com.devd.domain.usecase.report.GetMartComparisonUseCase
import com.devd.domain.usecase.report.GetPriceFluctuationUseCase
import com.devd.domain.usecase.report.GetPriceListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReportUiState(
    val isLoading: Boolean = false,
    val fluctuationInfo: FluctuationReport? = null,
    val martComparisonList: List<Pair<String, List<CategoryReport>>> = emptyList(),
    val priceList: List<SimplePriceInfo> = emptyList(),
) {
    fun isContentEmpty() =
        fluctuationInfo == null || martComparisonList.size < 2 || priceList.size < 2
}

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val getPriceFluctuationUseCase: GetPriceFluctuationUseCase,
    private val getMartComparisonUseCase: GetMartComparisonUseCase,
    private val getPriceListUseCase: GetPriceListUseCase,
    private val fetchProductNameListUseCase: FetchProductNameListUseCase
) : ViewModel() {

    private val _uiSate = MutableStateFlow(ReportUiState())
    val uiState = _uiSate.asStateFlow()

    private var productList: List<ChartProductItem> = emptyList()

    fun getProductNameList() = productList

    fun loadFluctuationInfo() {
        viewModelScope.launch {
            val priceItem = getPriceFluctuationUseCase(RangePeriod.ONE_MONTH)
            val martCompareInfo = getMartComparisonUseCase()
            val chartList = collectPriceList(null)
            productList = fetchProductNameListUseCase()

            _uiSate.update {
                it.copy(
                    fluctuationInfo = priceItem,
                    martComparisonList = martCompareInfo,
                    priceList = chartList
                )
            }

        }
    }

    fun changeChartProduct(selectPos: Int, productName: String) {
        viewModelScope.launch {
            _uiSate.update {
                it.copy(isLoading = true)
            }
            productList = productList.mapIndexed { index, item ->
                item.copy(isSelect = index == selectPos)
            }
            val chartList = collectPriceList(productName)
            _uiSate.update {
                it.copy(isLoading = false, priceList = chartList)
            }
        }
    }

    suspend fun collectPriceList(product: String? = null): List<SimplePriceInfo> {
        return getPriceListUseCase(product)
    }
}