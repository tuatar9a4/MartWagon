package com.devd.common.ui.compare

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devd.common.R
import com.devd.domain.model.common.MessageItem
import com.devd.domain.model.database.PriceRecord
import com.devd.domain.usecase.record.InsertNewPriceRecordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PriceCompareUiState(
    val messageItem: MessageItem? = null
)

@HiltViewModel
class PriceCompareViewModel @Inject constructor(
    private val insertNewPriceRecordUseCase: InsertNewPriceRecordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PriceCompareUiState())
    val uiState = _uiState.asStateFlow()

    var shouldBackPage by mutableStateOf(false)

    fun addNewPrice(priceRecord: PriceRecord) {
        viewModelScope.launch {
            val result = insertNewPriceRecordUseCase(priceRecord)
            if (result == null) simpleMessageDialog(R.string.fail_insert_product)
            else {
                _uiState.update {
                    it.copy(
                        messageItem = MessageItem(
                            messageId = R.string.success_insert_product,
                            isCancelable = false,
                            onDismiss = ::dismissMessageDialog,
                            rightClick = {
                                dismissMessageDialog()
                                shouldBackPage = true
                            }
                        )
                    )
                }
            }
        }
    }

    fun simpleMessageDialog(messageId: Int) {
        _uiState.update {
            it.copy(
                messageItem = MessageItem(
                    messageId = messageId,
                    onDismiss = ::dismissMessageDialog,
                    rightClick = ::dismissMessageDialog
                )
            )
        }
    }

    fun dismissMessageDialog() {
        _uiState.update { it.copy(messageItem = null) }
    }
}