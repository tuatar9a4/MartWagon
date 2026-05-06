package com.devd.domain.model.common

data class MessageItem(
    val isCancelable : Boolean = true,
    val titleId: Int? = null,
    val messageId: Int,
    val rightTextID: Int? = null,
    val rightClick: () -> Unit,
    val onDismiss: () -> Unit,
    val leftTextId: Int? = null,
    val leftClick: (() -> Unit)? = null,
)