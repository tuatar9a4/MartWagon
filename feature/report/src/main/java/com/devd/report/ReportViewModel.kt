package com.devd.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devd.domain.model.report.FluctuationReport
import com.devd.domain.model.report.RangePeriod
import com.devd.domain.usecase.report.GetPriceFluctuationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReportUiState(
    val fluctuationInfo: FluctuationReport? = null
)

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val getPriceFluctuationUseCase: GetPriceFluctuationUseCase
) : ViewModel() {

    private val _uiSate = MutableStateFlow(ReportUiState())
    val uiState = _uiSate.asStateFlow()

    fun loadFluctuationInfo() {
        viewModelScope.launch {
            val priceItem = getPriceFluctuationUseCase(RangePeriod.ONE_MONTH)
            _uiSate.update { it.copy(fluctuationInfo = priceItem) }
        }
    }
}