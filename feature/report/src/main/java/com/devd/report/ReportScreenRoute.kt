package com.devd.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ReportScreenRoute(
    modifier: Modifier = Modifier
) {

    ReportScreen(
        modifier = modifier
    )
}

@Composable
fun ReportScreen(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.then(
            Modifier.fillMaxSize()
        )
    ) {
        Text("Report")
    }

}