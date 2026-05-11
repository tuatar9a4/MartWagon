package com.devd.common.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devd.common.theme.ColorWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicBottomDialog(
    sheetState: SheetState = rememberModalBottomSheetState(),
    properties: ModalBottomSheetProperties = ModalBottomSheetProperties(),
    onDismissRequest: () -> Unit,
    contents: @Composable () -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        properties = properties,
        containerColor = ColorWhite,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorWhite, RoundedCornerShape(topEnd = 15.dp, topStart = 15.dp))
                .padding(bottom = 20.dp)
        ) {
            contents()
        }

    }
}