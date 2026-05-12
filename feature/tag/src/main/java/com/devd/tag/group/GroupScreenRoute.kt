package com.devd.tag.group

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devd.common.theme.ColorBackground
import com.devd.domain.model.group.GroupType
import com.devd.tag.group.screen.CategoryGroup
import com.devd.tag.group.screen.GroupSelector
import com.devd.tag.group.screen.GroupTopBanner
import com.devd.tag.group.screen.MartGroupList
import timber.log.Timber

sealed interface GroupScreenAction {
    data class ChangeGroup(val index: Int) : GroupScreenAction
    data class MoveToDetail(val itemName: String) : GroupScreenAction
    data object MoveToSetting : GroupScreenAction
}

@Composable
fun GroupScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: GroupViewModel = hiltViewModel(),
    onMoveDetailPage: (GroupType, String) -> Unit,
    onMoveSetting: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        GroupScreen(
            uiState = uiState,
            onAction = { action ->
                when (action) {
                    is GroupScreenAction.ChangeGroup -> {
                        Timber.d("CheckIndex ChangeGroup : ${(action.index)}")
                        viewModel.changeGroup(action.index)
                    }

                    is GroupScreenAction.MoveToDetail ->
                        onMoveDetailPage(uiState.selectGroupType, action.itemName)

                    GroupScreenAction.MoveToSetting -> onMoveSetting()
                }
            }
        )
    }

}

@Composable
fun GroupScreen(
    uiState: GroupUiState,
    onAction: (GroupScreenAction) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)
            .padding(horizontal = 20.dp)
    ) {
        GroupTopBanner(
            clickSetting = {
                onAction(GroupScreenAction.MoveToSetting)
            }
        )
        Spacer(Modifier.height(20.dp))
        GroupSelector(
            selectedIndex = uiState.selectGroupType,
            changeGroup = {
                onAction(GroupScreenAction.ChangeGroup(it))
            }
        )
        Spacer(Modifier.height(20.dp))
        when (uiState.selectGroupType) {
            GroupType.MART -> MartGroupList(
                groupList = uiState.martList,
                itemsMap = uiState.martItems,
                onItemClick = { onAction(GroupScreenAction.MoveToDetail(it)) }
            )

            GroupType.CATEGORY -> CategoryGroup(
                categoryList = uiState.categoryList,
                itemsMap = uiState.categoryItems,
                onItemClick = { onAction(GroupScreenAction.MoveToDetail(it)) }
            )
        }
        Spacer(Modifier.height(20.dp))
    }

}

@Preview
@Composable
fun GroupScreenPreview() {
    GroupScreen(
        uiState = GroupUiState(),
        onAction = {

        }
    )
}