package com.devd.martwagon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.devd.common.R
import com.devd.common.theme.ColorBackground
import com.devd.common.theme.ColorWhite
import com.devd.common.theme.MartWagonTheme
import com.devd.home.navigation.RecordNav
import com.devd.home.record.RecordScreenRoute
import com.devd.martwagon.screen.BottonNaviItem
import com.devd.report.ReportScreenRoute
import com.devd.report.navigation.ReportNavs
import com.devd.setting.SettingScreenRoute
import com.devd.setting.navigation.SettingNavs
import com.devd.tag.TagScreenRoute
import com.devd.tag.navigation.TagNavs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MartWagonTheme {
                val backStack = rememberNavBackStack(RecordNav)
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(
                            containerColor = ColorWhite
                        ) {
                            BottonNaviItem(
                                isSelect = backStack.lastOrNull() is RecordNav,
                                icon = R.drawable.icon_home,
                                label = R.string.tab_record,
                                onClick = {
                                    val index = backStack.indexOfFirst { it is RecordNav }
                                    if (index != -1) {
                                        // 백 스택에 이미 있으면 그 위치로 돌아간다
                                        while (backStack.size > index + 1) {
                                            backStack.removeAt(backStack.size - 1)
                                        }
                                    } else {
                                        backStack.add(RecordNav)
                                    }
                                },
                            )
                            BottonNaviItem(
                                isSelect = backStack.lastOrNull() is ReportNavs,
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
                            BottonNaviItem(
                                isSelect = backStack.lastOrNull() is TagNavs,
                                icon = R.drawable.icon_tag,
                                label = R.string.tab_tag,
                                onClick = {
                                    val index = backStack.indexOfFirst { it is TagNavs }
                                    if (index != -1) {
                                        // 백 스택에 이미 있으면 그 위치로 돌아간다
                                        while (backStack.size > index + 1) {
                                            backStack.removeAt(backStack.size - 1)
                                        }
                                    } else {
                                        backStack.add(TagNavs)
                                    }
                                },
                            )
                            BottonNaviItem(
                                isSelect = backStack.lastOrNull() is SettingNavs,
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
                ) { innerPadding ->

                    val paddingModifier = Modifier
                        .background(ColorBackground)
                        .padding(innerPadding)

                    NavDisplay(
                        backStack = backStack,
                        onBack = {
                            if (backStack.size > 1) {
                                backStack.removeAt(backStack.size - 1)
                            }
                        },
                        entryProvider = entryProvider {
                            entry<RecordNav> {
                                RecordScreenRoute(
                                    modifier = paddingModifier
                                )
                            }
                            entry<ReportNavs> {
                                ReportScreenRoute(
                                    modifier = paddingModifier
                                )
                            }
                            entry<TagNavs> {
                                TagScreenRoute(
                                    modifier = paddingModifier
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
        }
    }
}
