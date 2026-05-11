package com.devd.tag.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devd.domain.model.database.PriceRecord
import com.devd.domain.model.datastore.SavedMartData
import com.devd.domain.model.group.GroupType
import com.devd.domain.usecase.record.FetchPriceRecordListUseCase
import com.devd.domain.usecase.store.GetSavedMetaDataListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class GroupUiState(
    val isLoading: Boolean = false,
    val selectGroupType: GroupType = GroupType.MART,
    val martList: List<SavedMartData> = emptyList(),
    val categoryList: List<String> = emptyList(),
    val martItems: Map<String, List<PriceRecord>> = mapOf(),
    val categoryItems: Map<String, List<PriceRecord>> = mapOf()
)

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val getSavedMetaDataListUseCase: GetSavedMetaDataListUseCase,
    private val fetchPriceRecordListUseCase: FetchPriceRecordListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GroupUiState())
    val uiState: StateFlow<GroupUiState> =
        combine(_uiState, fetchPriceRecordListUseCase()){ uiState , records ->
            val martItems = records.groupBy { it.martName }
            val categoryItems = records.groupBy { it.category }
            uiState.copy(
                martItems = martItems,
                categoryItems = categoryItems
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // 성능 최적화
            initialValue = GroupUiState()
        )

    init {
        initGroupMetadata()
    }

    fun changeGroup(index: Int) {
        Timber.d("CheckIndex ChangeGroup : ${(GroupType.entries[index])}")

        _uiState.update { it.copy(selectGroupType = GroupType.entries[index]) }
    }

    fun initGroupMetadata() {
        viewModelScope.launch {
            val savedData = getSavedMetaDataListUseCase()
            _uiState.update {
                it.copy(
                    martList = savedData?.martList ?: emptyList(),
                    categoryList = savedData?.category ?: emptyList()
                )
            }

        }
    }

}
