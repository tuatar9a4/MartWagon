package com.devd.common.ui.compare.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devd.common.R
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorSemiBlue
import com.devd.common.theme.ColorWhite
import com.devd.common.util.RoundedCard
import com.devd.domain.model.database.PriceUnit
import java.text.DecimalFormat

@Preview
@Composable
fun InputCurPricePreview() {
    var curPrice by remember { mutableLongStateOf(-1) }

    InputCurPrice(
        curPrice = curPrice,
        quantity = 0,
        priceUnit = PriceUnit.Milliliter,
        onValueChange = { curPrice = it },
        onQuantityChange = {},
        onActionDone = {}
    )
}

@Composable
fun InputCurPrice(
    curPrice: Long,
    quantity: Long,
    priceUnit: PriceUnit?,
    onValueChange: (Long) -> Unit,
    onQuantityChange: (Long) -> Unit,
    onActionDone: () -> Unit
) {
    val pricePerUnit =
        if (quantity == 0L || quantity == -1L || curPrice == 0L || curPrice == -1L || priceUnit == null) null
        else {
            val formatter = DecimalFormat("#,###")
            formatter.format((curPrice.toFloat() * priceUnit.step / quantity))
        }

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
        InputNumberWithTail(
            modifier = Modifier,
            text = curPrice.toLong(),
            onNumberChange = onValueChange,
            placeHolderStr = R.string.new_price,
            tailStr = R.string.currency_unit,
            imeAction = if (priceUnit == null) ImeAction.Done else ImeAction.Next,
            onDone = onActionDone
        )
        priceUnit?.let {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.current_quantity_text),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ColorSemiBlue
                    )
                )
                Spacer(Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                ) {
                    InputNumberWithTail(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        text = quantity,
                        onNumberChange = onQuantityChange,
                        placeHolderStr = R.string.quantity_placeholder,
                        tailStr = priceUnit.display,
                        imeAction = ImeAction.Done,
                        onDone = onActionDone
                    )
                    Spacer(Modifier.width(10.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(ColorSemiBlue.copy(0.3f), RoundedCornerShape(10.dp))
                            .padding(vertical = 5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${priceUnit.step}${stringResource(priceUnit.display)}" +
                                    stringResource(R.string.per_text),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ColorWhite
                            )
                        )
                        Spacer(Modifier.height(5.dp))
                        Text(
                            text = pricePerUnit?.let { "$it ${stringResource(R.string.currency_unit)}" }
                                ?: "-",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = ColorWhite
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InputNumberWithTail(
    modifier: Modifier,
    text: Long,
    onNumberChange: (Long) -> Unit,
    placeHolderStr: Int,
    tailStr: Int,
    imeAction: ImeAction,
    onDone: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .border(1.dp, ColorSemiBlue.copy(0.2f), RoundedCornerShape(10.dp))
            .background(ColorWhite.copy(alpha = 0.1f), RoundedCornerShape(10.dp))
            .padding(vertical = 10.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = if (text == -1L) "" else text.toString(),
            onValueChange = {
                if (it.all { it.isDigit() }) onNumberChange(it.ifEmpty { "-1" }.toLong())
            },
            modifier = Modifier
                .weight(1f)
                .padding(10.dp),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                color = ColorSemiBlue
            ),
            keyboardActions = KeyboardActions(
                onDone = { onDone() }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = imeAction
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box() {
                        // 입력값이 비어있을 때 Placeholder 표시
                        if (text == -1L) {
                            Text(
                                text = stringResource(placeHolderStr),
                                maxLines = 1,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ColorSemiBlue.copy(alpha = 0.5f)
                                ),
                                autoSize = TextAutoSize.StepBased(7.sp, 14.sp)
                            )
                        }
                        // 실제 입력 필드가 그려지는 위치
                        innerTextField()
                    }

                }
            }
        )
        Text(
            text = stringResource(tailStr),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                color = ColorSemiBlue
            )
        )
    }
}