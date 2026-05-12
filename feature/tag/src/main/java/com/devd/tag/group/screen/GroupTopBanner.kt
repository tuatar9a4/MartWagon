package com.devd.tag.group.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devd.common.R
import com.devd.common.theme.ColorSecondaryText
import com.devd.common.theme.ColorWhite

@Composable
fun GroupTopBanner(
    clickSetting: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            modifier = Modifier.height(25.dp),
            painter = painterResource(R.drawable.img_group_banner),
            contentDescription = null,
        )
        Image(
            modifier = Modifier
                .clickable(onClick = clickSetting)
                .size(36.dp)
                .background(ColorWhite, RoundedCornerShape(15.dp))
                .padding(10.dp),
            painter = painterResource(R.drawable.icon_setting),
            contentDescription = null,
            colorFilter = ColorFilter.tint(ColorSecondaryText)
        )
    }
}