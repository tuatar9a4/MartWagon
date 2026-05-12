package com.devd.setting.utils

import com.devd.common.R

enum class SettingItemType(val display: Int) {
    SAVED_MART_LIST(R.string.saved_mart_management),
    SAVED_CATEGORY_LIST(R.string.saved_category_management),
    RECORD_RESET(R.string.record_reset)
}

data class SettingItem(
    val type: SettingItemType

)

val settingItems = listOf(
    SettingItem(SettingItemType.SAVED_MART_LIST),
    SettingItem(SettingItemType.SAVED_CATEGORY_LIST),
    SettingItem(SettingItemType.RECORD_RESET)
)