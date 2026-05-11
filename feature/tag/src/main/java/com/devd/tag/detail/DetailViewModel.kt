package com.devd.tag.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devd.common.R
import com.devd.domain.model.common.MessageItem
import com.devd.domain.model.database.PriceRecord
import com.devd.domain.model.group.GroupType
import com.devd.domain.usecase.record.DeletePriceRecordUseCase
import com.devd.domain.usecase.record.FetchGroupListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailUiState(
    val detailList: List<PriceRecord> = emptyList(),
    val messageItem: MessageItem? = null
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val fetchGroupListUseCase: FetchGroupListUseCase,
    private val deletePriceRecordUseCase: DeletePriceRecordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState.asStateFlow()

    fun getDetailList(type: GroupType, itemName: String) {
        viewModelScope.launch {
            fetchGroupListUseCase(groupType = type, itemName = itemName).collect { resultList ->
                _uiState.update { it.copy(detailList = resultList) }
            }
        }
    }

    fun askDeleteItem(deleteId: Long) {
        _uiState.update {
            it.copy(
                messageItem = MessageItem(
                    messageId = R.string.ask_delete_record,
                    rightTextID = R.string.confirm,
                    rightClick = { deleteItem(deleteId) },
                    leftTextId = R.string.cancel,
                    leftClick = { dismissMessageDialog() },
                    onDismiss = { dismissMessageDialog() },
                )
            )
        }
    }

    private fun dismissMessageDialog() {
        _uiState.update { it.copy(messageItem = null) }
    }

    fun deleteItem(deleteId: Long) {
        viewModelScope.launch {
            deletePriceRecordUseCase(deleteId)
            _uiState.update {
                it.copy(
                    messageItem = MessageItem(
                        messageId = R.string.success_delete_record,
                        rightTextID = R.string.confirm,
                        rightClick = { dismissMessageDialog() },
                        onDismiss = { dismissMessageDialog() },
                    )
                )
            }
        }
    }

}