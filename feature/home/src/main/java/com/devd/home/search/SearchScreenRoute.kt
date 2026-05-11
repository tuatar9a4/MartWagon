package com.devd.home.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.devd.common.R
import com.devd.common.theme.ColorBackground
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorTertiaryText
import com.devd.common.theme.ColorWhite
import com.devd.common.ui.dialog.ShowLoadingDialog
import com.devd.home.search.screen.RecentSearchWordList
import com.devd.home.search.screen.SearchResultList

sealed interface SearchAction {
    data class OnSearchWord(val word: String) : SearchAction
    data class OnDeleteSearchWord(val word: String) : SearchAction
    data object OnDeleteAllSearchWord : SearchAction
    data object OnClearSearchList : SearchAction
    data object BackAction : SearchAction
}

@Composable
fun SearchScreenRoute(
    modifier: Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    SearchScreen(
        modifier = modifier,
        uiState = uiState,
        searchActionListener = {
            when (it) {
                SearchAction.OnDeleteAllSearchWord -> viewModel.deleteAllWord()
                is SearchAction.OnDeleteSearchWord -> viewModel.deleteWord(it.word)
                is SearchAction.OnSearchWord -> viewModel.updateNewWord(it.word)
                SearchAction.OnClearSearchList -> viewModel.clearSearchList()
                SearchAction.BackAction -> onBackClick()
            }
        }
    )

    uiState.isLoading.ShowLoadingDialog()
}


@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    uiState: SearchUiState,
    searchActionListener: (SearchAction) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ColorBackground)
    ) {
        Row(
            modifier = Modifier
                .background(ColorWhite)
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.clickable(onClick = { searchActionListener(SearchAction.BackAction) }),
                painter = painterResource(R.drawable.icon_left_arrow),
                contentDescription = null,
                colorFilter = ColorFilter.tint(ColorMainText)
            )
            Spacer(Modifier.width(20.dp))
            Box(
                modifier = Modifier.weight(1f)
            ) {
                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = uiState.searchRecordList == null,
                    value = searchText,
                    onValueChange = { searchText = it },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = ColorMainText
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            searchActionListener(SearchAction.OnSearchWord(searchText))
                            focusManager.clearFocus()
                        }
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .background(ColorBackground, RoundedCornerShape(10.dp))
                                .padding(10.dp)
                                .padding(end = 25.dp)
                        ) {
                            if (searchText.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.search_word_placeholder),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = ColorTertiaryText
                                    )
                                )
                            } else {
                                innerTextField()
                            }
                        }
                    }
                )
                if (uiState.searchRecordList != null) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable(onClick = {
                                searchActionListener(SearchAction.OnClearSearchList)
                                searchText = ""
                            })
                            .size(30.dp)
                            .padding(6.dp),
                        painter = painterResource(R.drawable.icon_close),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(ColorTertiaryText)
                    )
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        uiState.searchRecordList?.let {
            SearchResultList(
                searchWord = searchText,
                searchList = it
            )
        } ?: run {
            RecentSearchWordList(
                searchWordList = uiState.searchWordList,
                onSearchWord = {
                    searchText = it
                    searchActionListener(SearchAction.OnSearchWord(it))
                },
                onDeleteSearchWord = { searchActionListener(SearchAction.OnDeleteSearchWord(it)) },
                onDeleteAllSearchWord = { searchActionListener(SearchAction.OnDeleteAllSearchWord) }
            )
        }
    }

}


@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(
        uiState = SearchUiState(
            searchWordList = listOf("유류", "삼겹살", "소고기", "맛있겠다")
        ),
        searchActionListener = {}
    )
}