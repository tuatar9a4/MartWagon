package com.devd.common.ui.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.DialogProperties
import com.devd.common.R
import com.devd.common.theme.ColorMainText
import com.devd.domain.model.common.MessageItem

@Composable
fun MessageDialog(
    messageItem: MessageItem
) {
    CommonDialog(
        properties = DialogProperties(
            dismissOnBackPress = messageItem.isCancelable,
            dismissOnClickOutside = messageItem.isCancelable
        ),
        dialogTitle = messageItem.titleId,
        onLeftText = messageItem.leftTextId ?: R.string.cancel,
        onLeftClick = messageItem.leftClick,
        onRightText = messageItem.rightTextID ?: R.string.confirm,
        onRightClick = messageItem.rightClick,
        onDismiss = messageItem.onDismiss
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(messageItem.messageId),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = ColorMainText,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
fun MessageItem?.ShowMessageDialog() {
    this ?: return
    MessageDialog(this)
}