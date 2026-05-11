package com.devd.tag.navigation

import androidx.navigation3.runtime.NavKey
import com.devd.domain.model.group.GroupType

data object GroupNavs : NavKey
data class DetailNavs(
    val detailType: GroupType,
    val itemName: String
) : NavKey
