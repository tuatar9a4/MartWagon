package com.devd.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devd.common.R
import com.devd.domain.model.common.MessageItem
import com.devd.domain.model.datastore.RegisterMetadata
import com.devd.domain.model.datastore.SavedMartData
import com.devd.domain.usecase.record.DeleteAllPriceRecordUseCase
import com.devd.domain.usecase.store.GetSavedMetaDataListUseCase
import com.devd.domain.usecase.store.SaveSavedMetaDataListUseCase
import com.devd.setting.ChangeMetaType.CATEGORY
import com.devd.setting.ChangeMetaType.MART
import com.devd.setting.ChangeMetaType.NONE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

enum class ChangeMetaType {
    NONE, MART, CATEGORY
}

data class SettingUiState(
    val isLoading: Boolean = false,
    val messageItem: MessageItem? = null,
    val showMetaType: ChangeMetaType = ChangeMetaType.NONE,
)

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val deleteAllPriceRecordUseCase: DeleteAllPriceRecordUseCase,
    private val getSavedMetaDataListUseCase: GetSavedMetaDataListUseCase,
    private val savedMetaDataListUseCase: SaveSavedMetaDataListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()

    var savedMartList by mutableStateOf<List<SavedMartData>>(emptyList())
    var savedCategoryList by mutableStateOf<List<String>>(emptyList())

    fun loadSavedMetaData() {
        viewModelScope.launch {
            val savedMetadata = getSavedMetaDataListUseCase()
            savedMartList = savedMetadata?.martList ?: emptyList()
            savedCategoryList = savedMetadata?.category ?: emptyList()
        }
    }

    fun showChangeMetaPopup(type: ChangeMetaType) {
        _uiState.update {
            it.copy(showMetaType = type)
        }
    }

    fun insertItemInMetaList(type: ChangeMetaType, item: String) {
        viewModelScope.launch {
            when (type) {
                NONE -> Unit
                MART ->
                    savedMartList =
                        savedMartList + listOf(SavedMartData(item, Random.nextInt(0, 9)))

                CATEGORY ->
                    savedCategoryList = savedCategoryList + item
            }
            savedMetaDataListUseCase(
                RegisterMetadata(
                    martList = savedMartList,
                    category = savedCategoryList
                )
            )
        }
    }

    fun deleteItemInMetaItem(type: ChangeMetaType, item: String) {
        viewModelScope.launch {
            when (type) {
                NONE -> Unit
                MART ->
                    savedMartList = savedMartList.toMutableList().apply {
                        removeIf { it.martName == item }
                    }

                CATEGORY ->
                    savedCategoryList = savedCategoryList.toMutableList().apply {
                        removeIf { it == item }
                    }
            }
            savedMetaDataListUseCase(
                RegisterMetadata(
                    martList = savedMartList,
                    category = savedCategoryList
                )
            )
        }
    }

    fun askResetPriceRecord() {
        _uiState.update {
            it.copy(
                messageItem = MessageItem(
                    messageId = R.string.ask_delete_record,
                    rightTextID = R.string.confirm,
                    rightClick = { resetRecordData() },
                    leftTextId = R.string.cancel,
                    leftClick = { dismissMessagePopup() },
                    onDismiss = { dismissMessagePopup() }
                )
            )
        }
    }

    fun resetRecordData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, messageItem = null) }
            deleteAllPriceRecordUseCase()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    messageItem = MessageItem(
                        messageId = R.string.success_delete_record,
                        rightClick = { dismissMessagePopup() },
                        onDismiss = { dismissMessagePopup() }
                    )
                )
            }
        }
    }

    fun dismissMessagePopup() {
        _uiState.update { it.copy(messageItem = null) }
    }

    fun dismissMetaChangePopup() {
        _uiState.update { it.copy(showMetaType = ChangeMetaType.NONE) }
    }


}