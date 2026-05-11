package com.devd.common.ui.textfield

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devd.common.R
import com.devd.common.theme.ColorBlackF8
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorTertiaryText

@Preview
@Composable
fun BorderTextFieldPreview() {
    BorderTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = "",
        onValueChange = {},
        placeHolder = R.string.currency_unit,
        suffix = {
            Text(text = "월")
        }
    )
}

@Composable
fun BorderTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    shape: Shape = RoundedCornerShape(10.dp),
    containerColor: Color = ColorBlackF8,
    borderColor: Color = ColorTertiaryText,
    textColor: Color = ColorMainText,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    @StringRes placeHolder: Int? = null,
    suffix: (@Composable () -> Unit)? = null,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = textColor
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        decorationBox = { innerTextField ->
            Box(
                modifier = modifier
                    .background(containerColor, shape)
                    .border(1.dp, borderColor, shape)
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        if (value.isEmpty() && placeHolder != null) {
                            Text(
                                text = stringResource(placeHolder),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = ColorTertiaryText
                                )
                            )
                        }
                        innerTextField()
                    }
                    if (suffix != null) {
                        Spacer(Modifier.width(5.dp))
                        suffix()
                    }
                }
            }
        }
    )
}