package com.devd.common.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.devd.common.R
import com.devd.common.theme.ColorDivider
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorTertiaryText
import com.devd.common.theme.ColorWhite

@Preview
@Composable
fun CommonDialogPreview() {
    CommonDialog(
        dialogTitle = null,
        onRightClick = {},
        onDismiss = {}
    ) { modifier ->
        Text(
            modifier = modifier,
            text = "이게 컨텐츠"
        )
    }
}

@Composable
fun CommonDialog(
    dialogTitle: Int? = null,
    properties: DialogProperties = DialogProperties(),
    onLeftText: Int = R.string.cancel,
    onLeftTextColor: Color = ColorTertiaryText,
    onLeftClick: (() -> Unit)? = null,
    onRightText: Int = R.string.confirm,
    onRightTextColor: Color = ColorMainText,
    onRightClick: () -> Unit,
    onDismiss: () -> Unit,
    dialogContent: @Composable (Modifier) -> Unit,
) {
    var touchCancel by remember { mutableStateOf(false) }
    Dialog(
        onDismissRequest = {
            if (touchCancel) return@Dialog
            onDismiss()
        },
        properties = properties
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = ColorWhite, shape = RoundedCornerShape(5.dp))
                .padding(top = 20.dp),
        ) {
            dialogTitle?.let {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    text = stringResource(dialogTitle),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = ColorMainText,
                        textAlign = TextAlign.Center
                    )
                )
            }
            dialogContent(
                Modifier.weight(1f, fill = false)
            )
            Spacer(Modifier.height(15.dp))
            HorizontalDivider(thickness = 1.dp, color = ColorDivider)
            Row(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                onLeftClick?.let {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(onClick = onLeftClick)
                            .padding(vertical = 10.dp),
                        text = stringResource(onLeftText),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = onLeftTextColor,
                            textAlign = TextAlign.Center
                        )
                    )
                    VerticalDivider(thickness = 1.dp, color = ColorDivider)
                }
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = {
                            touchCancel = true
                            onRightClick()
                        })
                        .padding(vertical = 10.dp),
                    text = stringResource(onRightText),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = onRightTextColor,
                        textAlign = TextAlign.Center
                    )
                )

            }
        }

    }
}