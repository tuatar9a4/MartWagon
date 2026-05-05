package com.devd.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingScreenRoute(
    modifier: Modifier = Modifier
) {

    SettingScreen(
        modifier = modifier
    )
}

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.then(
            Modifier.fillMaxSize()
        )
    ) {
        Text("Setting")
    }

}