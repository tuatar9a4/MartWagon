package com.devd.common.ui.register.screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devd.common.R
import com.devd.common.theme.ColorDeepDarkBlue
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorSemiBlue
import com.devd.common.theme.ColorTertiaryText
import com.devd.common.theme.ColorWhite
import com.devd.common.util.LabelText
import com.devd.common.util.RoundedCard
import com.devd.domain.model.database.PriceUnit

@Preview
@Composable
fun InputQuantityInfoPreview() {
    var quantity by remember { mutableStateOf(-1) }
    InputQuantityInfo(
        price = 5000,
        quantity = quantity,
        selectedIndex = 0,
        onQuantityUpdate = {
            quantity = it
        },
        onQuantityUnitUpdate = {}
    )
}

@Composable
fun InputQuantityInfo(
    price: Int,
    quantity: Int,
    selectedIndex: Int,
    onQuantityUpdate: (newQuantity: Int) -> Unit,
    onQuantityUnitUpdate: (index: Int) -> Unit
) {

    val focusManager = LocalFocusManager.current
    val selectUnit = PriceUnit.entries[selectedIndex]

    RoundedCard(
        containerColor = ColorDeepDarkBlue,
        borderColor = ColorDeepDarkBlue
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LabelText(
                labelIcon = R.drawable.icon_package,
                label = stringResource(R.string.option_quantity),
                iconColor = ColorPrimaryBlue
            )
            UnitSelector(
                selectedIndex = selectedIndex,
                onUnitSelected = onQuantityUnitUpdate
            )
        }
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .heightIn(min = 80.dp)
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(
                        ColorWhite.copy(alpha = 0.1f),
                        RoundedCornerShape(20.dp)
                    )
                    .border(1.dp, ColorWhite.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    modifier = Modifier.weight(1f),
                    value = quantity.takeIf { it != -1 }?.toString() ?: "",
                    onValueChange = {
                        if (it.all { it.isDigit() }) onQuantityUpdate(it.ifEmpty { "-1" }.toInt())
                    },
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = ColorWhite
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
                        Box(
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (quantity.takeIf { it != -1 }?.toString().isNullOrBlank()) {
                                Text(
                                    text = stringResource(R.string.quantity_placeholder),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = ColorSemiBlue.copy(alpha = 0.5f)
                                    )
                                )
                            }
                            innerTextField()
                        }
                    }
                )
                Text(
                    text = stringResource(selectUnit.display),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = ColorSemiBlue
                    )
                )
            }
            Spacer(Modifier.width(30.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(
                        ColorWhite.copy(alpha = 0.1f),
                        RoundedCornerShape(20.dp)
                    )
                    .border(1.dp, ColorWhite.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${selectUnit.step}${stringResource(selectUnit.display)}" +
                            stringResource(R.string.step_tail_message),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = ColorTertiaryText
                    )
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = if (quantity == -1 || quantity == 0 || price == -1) "-"
                    else "${price * selectUnit.step / quantity} ${stringResource(R.string.currency_unit)}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = ColorWhite
                    )
                )
            }

        }

    }
}

@Composable
fun UnitSelector(
    selectedIndex: Int,
    onUnitSelected: (Int) -> Unit
) {
    val units = PriceUnit.entries.map { it.display }

    Box(
        modifier = Modifier
            .background(Color(0xFF2C313D), RoundedCornerShape(12.dp))
            .padding(4.dp)
    ) {
        // 1. 선택된 배경 (Indicator)
        val xOffset by animateDpAsState(
            targetValue = 30.dp * selectedIndex,
            animationSpec = spring(stiffness = Spring.StiffnessLow),
            label = "indicator"
        )

        Box(
            modifier = Modifier
                .offset(x = xOffset)
                .width(30.dp)
                .height(30.dp)
                .background(ColorPrimaryBlue, RoundedCornerShape(8.dp))
        )
        // 2. 텍스트 레이어
        Row() {
            units.forEachIndexed { index, unit ->
                Box(
                    modifier = Modifier
                        .size(width = 30.dp, height = 30.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null // 클릭 리플 효과 제거 (인디케이터가 움직이므로)
                        ) { onUnitSelected(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(unit),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color.White,
                            fontWeight = if (index == selectedIndex) FontWeight.Bold else FontWeight.Normal
                        )
                    )
                }
            }
        }
    }
}