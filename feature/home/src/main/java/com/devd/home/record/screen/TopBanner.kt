package com.devd.home.record.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devd.common.R
import com.devd.common.theme.ColorBlack1B
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorSecondaryText
import com.devd.common.theme.ColorSemiBlue

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
            .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(R.drawable.icon_location),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(ColorPrimaryBlue)
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = stringResource(R.string.my_location_mart_record),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = ColorSecondaryText,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
            Spacer(Modifier.height(5.dp))
            Image(
                modifier = Modifier.height(25.dp),
                painter = painterResource(R.drawable.img_home_banner),
                contentScale = ContentScale.FillHeight,
                contentDescription = null
            )
        }
        Image(
            modifier = Modifier
                .background(ColorSemiBlue, CircleShape)
                .clip(CircleShape)
                .clickable(onClick = onClickSearch)
                .size(30.dp)
                .padding(6.dp),
            painter = painterResource(R.drawable.icon_search),
            contentDescription = null,
            colorFilter = ColorFilter.tint(ColorBlack1B)
        )
    }
}