package com.devd.common.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun LoadingDialog(
    onDismissRequest: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = false, // 뒤로가기 버튼으로 닫기 방지 (선택)
            dismissOnClickOutside = false // 외부 클릭으로 닫기 방지
        )
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.7f), // 반투명 배경
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 4.dp
            )
        }
    }
}

@Composable
fun Boolean.ShowLoadingDialog(onDismissRequest: () -> Unit = {}) {
    if (!this) return
    LoadingDialog(onDismissRequest = onDismissRequest)
}