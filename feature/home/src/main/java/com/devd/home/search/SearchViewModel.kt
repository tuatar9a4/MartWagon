package com.devd.home.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devd.domain.model.database.PriceRecord
import com.devd.domain.usecase.record.FetchSearchPriceRecordListUseCase
import com.devd.domain.usecase.store.GetSearchWordListUseCase
import com.devd.domain.usecase.store.SaveSearchWordListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val isLoading: Boolean = false,
    val searchWordList: List<String> = emptyList(),
    val searchRecordList: List<PriceRecord>? = null,
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchWordListUseCase: GetSearchWordListUseCase,
    private val saveSearchWordListUseCase: SaveSearchWordListUseCase,
    private val fetchSearchPriceRecordListUseCase: FetchSearchPriceRecordListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private val _searchWord = MutableStateFlow("")

    private var searchJob: Job? = null

    init {
        collectSearchWordList()
    }

    fun collectSearchWordList() {
        viewModelScope.launch {
            getSearchWordListUseCase().collect {
                _uiState.value = _uiState.value.copy(searchWordList = it)
            }
        }
    }

    fun updateNewWord(newWord: String) {
        viewModelScope.launch {
            val currentList = _uiState.value.searchWordList.toMutableList()
            currentList.removeIf { it == newWord }
            currentList.add(0, newWord)
            saveSearchWordListUseCase(currentList)
            searchPriceRecordList(newWord)
        }
    }

    fun deleteWord(deleteWord: String) {
        viewModelScope.launch {
            val currentList = _uiState.value.searchWordList.toMutableList()
            currentList.remove(deleteWord)
            saveSearchWordListUseCase(currentList)
        }
    }

    fun deleteAllWord() {
        viewModelScope.launch {
            saveSearchWordListUseCase(emptyList())
        }
    }

    fun searchPriceRecordList(searchWord: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            fetchSearchPriceRecordListUseCase(searchWord).collect { list ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        searchRecordList = list
                    )
                }
            }
        }
    }

    fun clearSearchList() {
        searchJob?.cancel()
        _uiState.update { it.copy(searchRecordList = null) }
    }

}