package com.devd.tag.group

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devd.common.R
import com.devd.common.theme.ColorBackground
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorWhite
import com.devd.common.ui.register.PriceRegisterPopup
import com.devd.domain.model.group.GroupType
import com.devd.tag.group.screen.CategoryGroup
import com.devd.tag.group.screen.GroupSelector
import com.devd.tag.group.screen.GroupTopBanner
import com.devd.tag.group.screen.MartGroupList
import timber.log.Timber

sealed interface GroupScreenAction {
    data class ChangeGroup(val index: Int) : GroupScreenAction
    data class MoveToDetail(val itemName: String) : GroupScreenAction
}

@Composable
fun GroupScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: GroupViewModel = hiltViewModel(),
    onMoveDetailPage: (GroupType, String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var isShowRegisterPopup by remember { mutableStateOf(false) }

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

                    is GroupScreenAction.MoveToDetail -> {
                        onMoveDetailPage(uiState.selectGroupType, action.itemName)
                    }
                }
            }
        )
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 10.dp),
            containerColor = ColorPrimaryBlue,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 2.dp),
            onClick = { isShowRegisterPopup = true }
        ) {
            Image(
                painter = painterResource(R.drawable.icon_add),
                contentDescription = null,
                colorFilter = ColorFilter.tint(ColorWhite)
            )
        }
    }

    // 등록 팝업
    if (isShowRegisterPopup) {
        PriceRegisterPopup(
            onDismiss = { isShowRegisterPopup = false },
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
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        GroupTopBanner()
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
                onItemClick = { onAction(GroupScreenAction.MoveToDetail(it))}
            )

            GroupType.CATEGORY -> CategoryGroup(
                categoryList = uiState.categoryList,
                itemsMap = uiState.categoryItems,
                onItemClick = { onAction(GroupScreenAction.MoveToDetail(it))}
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