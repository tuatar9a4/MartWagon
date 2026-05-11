package com.devd.record.record.screen

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devd.common.R
import com.devd.common.theme.ColorSecondaryText
import com.devd.common.theme.ColorWhite

@Preview
@Composable
fun TopBannerPreview() {
    TopBanner(
        onClickSearch = {}
    )
}

@Composable
fun TopBanner(
    onClickSearch: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.height(25.dp),
            painter = painterResource(R.drawable.img_home_banner),
            contentDescription = null,
        )
        Image(
            modifier = Modifier
                .background(ColorWhite, RoundedCornerShape(15.dp))
                .clip(RoundedCornerShape(15.dp))
                .clickable(onClick = onClickSearch)
                .size(36.dp)
                .padding(10.dp),
            painter = painterResource(R.drawable.icon_setting),
            contentDescription = null,
            colorFilter = ColorFilter.tint(ColorSecondaryText)
        )
    }
}