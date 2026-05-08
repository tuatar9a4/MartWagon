package com.devd.common.ui.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devd.common.R
import com.devd.domain.model.common.MessageItem
import com.devd.domain.model.database.PriceRecord
import com.devd.domain.usecase.record.InsertNewPriceRecordUseCase
import com.devd.domain.usecase.store.GetStoreListUseCase
import com.devd.domain.usecase.store.SaveStoreListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PriceRegisterUiState(
    val productName: String = "",
    val regularPrice: Long = -1,
    val purchasePrice: Long = -1,
    val selectStoreIndex: Int = 0,
    val quantity: Long = -1,
    val selectQuantityIndex: Int = 0,
    val storeList: List<String> = listOf(),
    val infoMemo: String = "",
    val messageItem: MessageItem? = null,
)

@HiltViewModel
class PriceRegisterViewModel @Inject constructor(
    private val getStoreListUseCase: GetStoreListUseCase,
    private val saveStoreListUseCase: SaveStoreListUseCase,
    private val insertNewPriceRecordUseCase: InsertNewPriceRecordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PriceRegisterUiState())
    val uiState = _uiState.asStateFlow()

    var shouldBackPage by mutableStateOf(false)

    init {
        initMartList()

    }

    fun initData() {
        shouldBackPage = false
        _uiState.update { PriceRegisterUiState(storeList = it.storeList) }
    }

    private fun initMartList() {
        viewModelScope.launch {
            val savedStoreList = getStoreListUseCase()
            if (savedStoreList.isNotEmpty()) _uiState.update { it.copy(storeList = savedStoreList) }
            else {
                val initList = listOf("이마트", "홈플러스", "코스트코")
                _uiState.update { it.copy(storeList = initList) }
                saveStoreListUseCase(initList)
            }
        }
    }

    fun updateProductName(name: String) {
        _uiState.update { it.copy(productName = name) }
    }

    fun updateRegularPrice(price: Long) {
        _uiState.update { it.copy(regularPrice = price) }
    }

    fun updatePurchasePrice(price: Long) {
        _uiState.update { it.copy(purchasePrice = price) }
    }

    fun updateSelectStore(storeIndex: Int) {
        _uiState.update { it.copy(selectStoreIndex = storeIndex) }
    }

    fun updateQuantity(quantity: Long) {
        _uiState.update { it.copy(quantity = quantity) }
    }

    fun updateSelectQuantity(quantityIndex: Int) {
        _uiState.update { it.copy(selectQuantityIndex = quantityIndex) }
    }

    fun addMartItem(addMart: String) {
        viewModelScope.launch {
            val updateList = uiState.value.storeList + listOf(addMart)
            _uiState.update {
                it.copy(selectStoreIndex = updateList.size - 1, storeList = updateList)
            }
            saveStoreListUseCase(updateList)
        }
    }

    fun updateMemo(memo: String) {
        _uiState.update { it.copy(infoMemo = memo) }
    }

    fun validateInputInfo() {
        viewModelScope.launch {
            val inputState = uiState.value
            if (inputState.productName.isEmpty()) return@launch simpleMessageDialog(R.string.need_product_name)
            if (inputState.purchasePrice == -1L) return@launch simpleMessageDialog(R.string.need_product_price)

            val result = insertNewPriceRecordUseCase.invoke(
                PriceRecord(
                    id = -1L,
                    productName = inputState.productName,
                    martName = inputState.storeList[inputState.selectStoreIndex],
                    originalPrice = inputState.regularPrice.takeIf { it != -1L },
                    currentPrice = inputState.purchasePrice,
                    memo = inputState.infoMemo,
                    recordDate = System.currentTimeMillis(),
                    quantity = inputState.quantity.takeIf { it != -1L },
                    unit = inputState.selectQuantityIndex,
                    discountRate = null
                )
            )

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