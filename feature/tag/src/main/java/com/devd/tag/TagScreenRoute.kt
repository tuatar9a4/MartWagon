package com.devd.tag

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TagScreenRoute(
    modifier: Modifier = Modifier
) {

    TagScreen(
        modifier = modifier
    )
}

@Composable
fun TagScreen(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.then(
            Modifier.fillMaxSize()
        )
    ) {
        Text("Tag")
    }

}