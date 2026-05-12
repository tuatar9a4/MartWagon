package com.devd.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devd.common.R
import com.devd.common.theme.ColorBackground
import com.devd.common.theme.ColorDivider
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorSecondaryText
import com.devd.common.ui.dialog.ShowMessageDialog
import com.devd.common.util.RoundedCard
import com.devd.common.util.getAppVersionName
import com.devd.setting.screen.SavedMetaChangePopup
import com.devd.setting.screen.SettingTopBanner
import com.devd.setting.utils.SettingItemType
import com.devd.setting.utils.SettingItemType.RECORD_RESET
import com.devd.setting.utils.SettingItemType.SAVED_CATEGORY_LIST
import com.devd.setting.utils.SettingItemType.SAVED_MART_LIST
import com.devd.setting.utils.settingItems

sealed interface SettingAction {
    data class OnClickSetting(val type: SettingItemType) : SettingAction
}

@Composable
fun SettingScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadSavedMetaData()
    }

    fun actionType(type: SettingItemType) {
        when (type) {
            SAVED_MART_LIST -> viewModel.showChangeMetaPopup(ChangeMetaType.MART)
            SAVED_CATEGORY_LIST -> viewModel.showChangeMetaPopup(ChangeMetaType.CATEGORY)
            RECORD_RESET -> viewModel.askResetPriceRecord()
        }
    }

    SettingScreen(
        modifier = modifier,
        onAction = {
            when (it) {
                is SettingAction.OnClickSetting -> actionType(it.type)
            }
        }
    )

    uiState.messageItem?.ShowMessageDialog()
    if (uiState.showMetaType != ChangeMetaType.NONE) {
        SavedMetaChangePopup(
            metaList = when (uiState.showMetaType) {
                ChangeMetaType.MART -> viewModel.savedMartList.map { it.martName }
                ChangeMetaType.CATEGORY -> viewModel.savedCategoryList
                else -> emptyList()
            },
            addMetaItem = {
                viewModel.insertItemInMetaList(uiState.showMetaType, it)
            },
            deleteMetaItem = {
                viewModel.deleteItemInMetaItem(uiState.showMetaType, it)
            },
            onClose= {
                viewModel.dismissMetaChangePopup()
            }
        )
    }
}

@Preview
@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    onAction: (SettingAction) -> Unit = {}
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ColorBackground)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SettingTopBanner()
        Spacer(Modifier.height(30.dp))
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            RoundedCard() {
                settingItems.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .clickable(
                                onClick = {onAction(SettingAction.OnClickSetting(item.type))},
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            )
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(item.type.display),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = ColorMainText
                            )
                        )
                        Image(
                            painter = painterResource(R.drawable.icon_right_action),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(ColorSecondaryText)
                        )
                    }
                    if (index != settingItems.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            color = ColorDivider
                        )
                    }
                }
            }
        }
        Text(
            text = "Mart Wagon v${context.getAppVersionName()}\nMade by DevD",
            style = MaterialTheme.typography.labelMedium.copy(
                color = ColorSecondaryText,
                textAlign = TextAlign.Center
            )
        )
        Spacer(Modifier.height(20.dp))
    }

}