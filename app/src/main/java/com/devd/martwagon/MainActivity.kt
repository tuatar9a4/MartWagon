package com.devd.martwagon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.devd.common.R
import com.devd.common.theme.ColorBackground
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorWhite
import com.devd.common.theme.MartWagonTheme
import com.devd.common.ui.register.PriceRegisterPopup
import com.devd.home.navigation.RecordNavs
import com.devd.home.navigation.SearchNav
import com.devd.home.record.RecordScreenRoute
import com.devd.home.search.SearchScreenRoute
import com.devd.martwagon.screen.BottonNaviItem
import com.devd.report.ReportScreenRoute
import com.devd.report.navigation.ReportNavs
import com.devd.setting.SettingScreenRoute
import com.devd.setting.navigation.SettingNavs
import com.devd.tag.detail.DetailScreenRoute
import com.devd.tag.group.GroupScreenRoute
import com.devd.tag.navigation.DetailNavs
import com.devd.tag.navigation.GroupNavs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            var isShowRegisterPopup by remember { mutableStateOf(false) }

            MartWagonTheme {
                val backStack = rememberNavBackStack(RecordNavs)
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        when (backStack.lastOrNull()) {
                            is RecordNavs,
                            is ReportNavs,
                            is GroupNavs,
                            is SettingNavs -> {
                                FloatingActionButton(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .offset(y = (60).dp)
                                        .shadow(
                                            elevation = 8.dp,
                                            shape = RoundedCornerShape(20.dp),
                                            ambientColor = ColorPrimaryBlue,
                                            spotColor = ColorPrimaryBlue,
                                            clip = false
                                        )
                                        .border(4.dp, ColorWhite, RoundedCornerShape(20.dp)),
                                    onClick = { isShowRegisterPopup = true },
                                    shape = RoundedCornerShape(20.dp),
                                    containerColor = ColorPrimaryBlue,
                                    contentColor = ColorWhite,
                                    elevation = FloatingActionButtonDefaults.elevation(
                                        defaultElevation = 0.dp
                                    ),
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.icon_add),
                                        contentDescription = "추가",
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                            else ->{}
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    bottomBar = {
                        when (val currentRoute = backStack.lastOrNull()) {
                            is RecordNavs,
                            is ReportNavs,
                            is GroupNavs,
                            is SettingNavs -> {
                                BottomAppBar(
                                    contentPadding = PaddingValues(horizontal = 16.dp),
                                    containerColor = ColorWhite
                                ) {
                                    BottonNaviItem(
                                        isSelect = currentRoute is RecordNavs,
                                        icon = R.drawable.icon_home,
                                        label = R.string.tab_record,
                                        onClick = {
                                            val index = backStack.indexOfFirst { it is RecordNavs }
                                            if (index != -1) {
                                                // 백 스택에 이미 있으면 그 위치로 돌아간다
                                                while (backStack.size > index + 1) {
                                                    backStack.removeAt(backStack.size - 1)
                                                }
                                            } else {
                                                backStack.add(RecordNavs)
                                            }
                                        },
                                    )
                                    BottonNaviItem(
                                        isSelect = currentRoute is ReportNavs,
                                        icon = R.drawable.icon_chart,
                                        label = R.string.tab_report,
                                        onClick = {
                                            val index = backStack.indexOfFirst { it is ReportNavs }
                                            if (index != -1) {
                                                // 백 스택에 이미 있으면 그 위치로 돌아간다
                                                while (backStack.size > index + 1) {
                                                    backStack.removeAt(backStack.size - 1)
                                                }
                                            } else {
                                                backStack.add(ReportNavs)
                                            }
                                        },
                                    )
                                    Spacer(Modifier.weight(1f))
                                    BottonNaviItem(
                                        isSelect = currentRoute is GroupNavs,
                                        icon = R.drawable.icon_tag,
                                        label = R.string.tab_tag,
                                        onClick = {
                                            val index = backStack.indexOfFirst { it is GroupNavs }
                                            if (index != -1) {
                                                // 백 스택에 이미 있으면 그 위치로 돌아간다
                                                while (backStack.size > index + 1) {
                                                    backStack.removeAt(backStack.size - 1)
                                                }
                                            } else {
                                                backStack.add(GroupNavs)
                                            }
                                        },
                                    )
                                    BottonNaviItem(
                                        isSelect = currentRoute is SettingNavs,
                                        icon = R.drawable.icon_setting,
                                        label = R.string.tab_setting,
                                        onClick = {
                                            val index = backStack.indexOfFirst { it is SettingNavs }
                                            if (index != -1) {
                                                // 백 스택에 이미 있으면 그 위치로 돌아간다
                                                while (backStack.size > index + 1) {
                                                    backStack.removeAt(backStack.size - 1)
                                                }
                                            } else {
                                                backStack.add(SettingNavs)
                                            }
                                        },
                                    )
                                }
                            }

                            else -> Unit
                        }
                    }
                ) { innerPadding ->

                    val paddingModifier = Modifier
                        .background(ColorBackground)
                        .padding(innerPadding)

                    NavDisplay(
                        backStack = backStack,
                        entryDecorators = listOf(
                            rememberViewModelStoreNavEntryDecorator()
                        ),
                        onBack = {
                            if (backStack.size > 1) {
                                backStack.removeAt(backStack.size - 1)
                            }
                        },
                        entryProvider = entryProvider {
                            entry<RecordNavs> {
                                RecordScreenRoute(
                                    modifier = paddingModifier,
                                    onMoveSearchPage = {
                                        backStack.add(SearchNav)
                                    }
                                )
                            }
                            entry<SearchNav> {
                                SearchScreenRoute(
                                    modifier = paddingModifier,
                                    onBackClick = {
                                        if (backStack.size > 1) backStack.removeAt(backStack.size - 1)
                                    }
                                )
                            }
                            entry<ReportNavs> {
                                ReportScreenRoute(
                                    modifier = paddingModifier
                                )
                            }
                            entry<GroupNavs> {
                                GroupScreenRoute(
                                    modifier = paddingModifier,
                                    onMoveDetailPage = { detailType, itemName ->
                                        backStack.add(DetailNavs(detailType, itemName))
                                    }
                                )
                            }
                            entry<DetailNavs> {
                                DetailScreenRoute(
                                    modifier = paddingModifier,
                                    detailType = it.detailType,
                                    itemName = it.itemName,
                                    onBackClick = {
                                        if (backStack.size > 1) backStack.removeAt(backStack.size - 1)
                                    }
                                )
                            }
                            entry<SettingNavs> {
                                SettingScreenRoute(
                                    modifier = paddingModifier
                                )
                            }
                        }
                    )
                }
            }
            if (isShowRegisterPopup) {
                PriceRegisterPopup(
                    onDismiss = { isShowRegisterPopup = false },
                )
            }
        }
    }
}
