package com.devd.martwagon.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.devd.common.R
import com.devd.common.ui.theme.ColorActive
import com.devd.common.ui.theme.ColorDisable
import com.devd.common.ui.theme.ColorSemiBlue

@Composable
fun RowScope.BottonNaviItem(
    isSelect: Boolean,
    icon: Int,
    label: Int,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = isSelect,
        onClick = onClick,
        colors = NavigationBarItemDefaults.colors(
            selectedTextColor = ColorActive,
            unselectedTextColor = ColorDisable,
            selectedIconColor = ColorActive,
            unselectedIconColor = ColorDisable,
            indicatorColor = Color.Transparent
        ),
        icon = {
            Image(
                modifier = Modifier
                    .background(color = if(isSelect) ColorSemiBlue else Color.Transparent , shape = CircleShape)
                    .size(36.dp)
                    .padding(6.dp),
                painter = painterResource(icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(if (isSelect) ColorActive else ColorDisable)
            )
        },
        label = {
            Text(
                text = stringResource(label),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = if (isSelect) ColorActive else ColorDisable
                )
            )
        },
    )
}