package com.devd.record.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devd.common.R
import com.devd.domain.model.common.MessageItem
import com.devd.domain.model.database.PriceRecord
import com.devd.domain.usecase.record.DeleteAllPriceRecordUseCase
import com.devd.domain.usecase.record.DeletePriceRecordUseCase
import com.devd.domain.usecase.record.FetchPriceRecordListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val recordItems: List<PriceRecord> = emptyList(),
    val recordItemCount: Int = 0,
    val recordLastTime: Long? = null,
    val messageItem: MessageItem? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchPriceRecordListUseCase: FetchPriceRecordListUseCase,
    private val deletePriceRecordUseCase: DeletePriceRecordUseCase,
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

    fun askDeleteRecordItem(deleteId: Long) {
        _uiState.update {
            it.copy(
                messageItem = MessageItem(
                    messageId = R.string.ask_delete_record,
                    leftClick = { dismissMessagePopup() },
                    rightClick = {
                        deleteRecordItem(deleteId)
                    },
                    onDismiss = { dismissMessagePopup() }
                )
            )
        }
    }

    fun deleteRecordItem(deleteId: Long) {
        viewModelScope.launch {
            deletePriceRecordUseCase(deleteId)
            showSimpleMessagePopup(R.string.success_delete_record)
        }
    }

    fun showSimpleMessagePopup(messageId: Int) {
        _uiState.update {
            it.copy(
                messageItem = MessageItem(
                    messageId = messageId,
                    onDismiss = { dismissMessagePopup() },
                    rightClick = { dismissMessagePopup() }
                )
            )
        }
    }

    fun dismissMessagePopup() {
        _uiState.update { it.copy(messageItem = null) }
    }

    fun tempDeleteAllItem() {
        viewModelScope.launch {
            deleteAllPriceRecordUseCase()
        }
    }
}
