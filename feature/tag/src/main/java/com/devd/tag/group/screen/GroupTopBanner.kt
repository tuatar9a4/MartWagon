package com.devd.tag.group.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devd.common.R
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorSecondaryText

@Composable
fun GroupTopBanner() {
    Column() {
        Text(
            text = stringResource(R.string.group_tab_title),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                color = ColorMainText
            )
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.group_tab_description),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = ColorSecondaryText
            )
        )
    }
}