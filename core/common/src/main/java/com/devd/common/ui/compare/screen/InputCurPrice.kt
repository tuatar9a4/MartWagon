package com.devd.common.ui.compare.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devd.common.R
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorSemiBlue
import com.devd.common.theme.ColorWhite
import com.devd.common.util.RoundedCard

@Preview
@Composable
fun InputCurPricePreview() {
    var curPrice by remember { mutableIntStateOf(-1) }

    InputCurPrice(
        curPrice = if (curPrice == -1) "" else curPrice.toString(),
        onValueChange = { curPrice = it }
    )
}

@Composable
fun InputCurPrice(
    curPrice: String,
    onValueChange: (Int) -> Unit
) {
    val focusManager = LocalFocusManager.current

    RoundedCard(
        containerColor = ColorPrimaryBlue
    ) {
        Text(
            text = stringResource(R.string.current_price_question),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                color = ColorSemiBlue
            )
        )
        Row(
            modifier = Modifier
                .border(1.dp, ColorSemiBlue, RoundedCornerShape(10.dp))
                .background(ColorWhite.copy(alpha = 0.1f), RoundedCornerShape(10.dp))
                .padding(vertical = 10.dp, horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = curPrice,
                onValueChange = {
                    if (it.all { it.isDigit() }) onValueChange(it.ifEmpty { "-1" }.toInt())
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = ColorSemiBlue
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box() {
                            // 입력값이 비어있을 때 Placeholder 표시
                            if (curPrice.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.new_price),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = ColorSemiBlue.copy(alpha = 0.5f)
                                    )
                                )
                            }
                            // 실제 입력 필드가 그려지는 위치
                            innerTextField()
                        }

                    }
                }
            )
            Text(
                text = stringResource(R.string.currency_unit),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = ColorSemiBlue
                )
            )
        }
    }
}