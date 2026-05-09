package com.devd.common.ui.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devd.common.R
import com.devd.domain.model.common.MessageItem
import com.devd.domain.model.database.PriceRecord
import com.devd.domain.model.datastore.RegisterMetadata
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
    val category: String = "",
    val registerMetadata: RegisterMetadata = RegisterMetadata(
        listOf("이마트", "홈플러스", "코스트코"),
        listOf("돼지고기", "콜라", "과자", "만두")
    ),
    val regularPrice: Long = -1,
    val purchasePrice: Long = -1,
    val selectStoreIndex: Int = 0,
    val quantity: Long = -1,
    val selectQuantityIndex: Int = 0,
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
        initSavedList()

    }

    fun initData() {
        shouldBackPage = false
        _uiState.update { PriceRegisterUiState() }
    }

    private fun initSavedList() {
        viewModelScope.launch {
            val savedMetaData = getStoreListUseCase() ?: run {
                val newMetadata = RegisterMetadata(
                    listOf("이마트", "홈플러스", "코스트코"),
                    listOf("돼지고기", "콜라", "과자", "만두")
                )
                saveStoreListUseCase(newMetadata)
                newMetadata
            }
            _uiState.update { it.copy(registerMetadata = savedMetaData) }
        }
    }

    fun updateProductName(name: String) {
        _uiState.update { it.copy(productName = name) }
    }

    fun updateCategory(category: String) {
        _uiState.update { it.copy(category = category) }
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

    fun updateMetadataItem(addMart: String? = null, addCategory: String? = null) {
        viewModelScope.launch {
            var metadata = uiState.value.registerMetadata
            var newStoreIndex = uiState.value.selectStoreIndex
            addMart?.let {
                metadata.martList.indexOf(addMart).takeIf { it == -1 }?.let {
                    newStoreIndex = metadata.martList.size
                    metadata = metadata.copy(martList = metadata.martList + addMart)
                }
            }
            addCategory?.let {
                metadata.category.indexOf(addCategory).takeIf { it == -1 }?.let {
                    metadata = metadata.copy(category = metadata.category + addCategory)
                }
            }

            _uiState.update { uiState ->
                uiState.copy(
                    category = addCategory ?: uiState.category,
                    selectStoreIndex = newStoreIndex,
                    registerMetadata = metadata
                )
            }
            saveStoreListUseCase(metadata)
        }
    }

    fun updateMemo(memo: String) {
        _uiState.update { it.copy(infoMemo = memo) }
    }

    fun validateInputInfo() {
        viewModelScope.launch {
            val inputState = uiState.value
            if (inputState.productName.isEmpty()) return@launch simpleMessageDialog(R.string.need_product_name)
            if (!inputState.registerMetadata.category.contains(inputState.category))
                return@launch simpleMessageDialog(R.string.need_product_category)
            if (inputState.purchasePrice == -1L) return@launch simpleMessageDialog(R.string.need_product_price)

            val result = insertNewPriceRecordUseCase.invoke(
                PriceRecord(
                    id = -1L,
                    productName = inputState.productName,
                    category = inputState.category,
                    martName = inputState.registerMetadata.martList[inputState.selectStoreIndex],
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