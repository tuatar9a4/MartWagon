package com.devd.home.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devd.domain.model.database.PriceRecord
import com.devd.domain.usecase.record.DeleteAllPriceRecordUseCase
import com.devd.domain.usecase.record.FetchPriceRecordListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val recordItems: List<PriceRecord> = emptyList(),
    val recordItemCount: Int = 0,
    val recordLastTime: Long? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchPriceRecordListUseCase: FetchPriceRecordListUseCase,
    private val deleteAllPriceRecordUseCase: DeleteAllPriceRecordUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        collectPriceRecord()
    }

    fun collectPriceRecord() {
        viewModelScope.launch {
            fetchPriceRecordListUseCase().collect {
                _uiState.value = _uiState.value.copy(
                    recordItems = it,
                    recordLastTime = it.lastOrNull()?.recordDate,
                    recordItemCount = it.size
                )
            }
        }
    }

    fun tempDeleteAllItem() {
        viewModelScope.launch {
            deleteAllPriceRecordUseCase()
        }
    }
}
