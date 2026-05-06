package com.devd.common.ui.register.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.devd.common.R
import com.devd.common.theme.ColorBlackF8
import com.devd.common.theme.ColorDisable
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorSecondaryText
import com.devd.common.theme.ColorSemiBlue
import com.devd.common.theme.ColorTertiaryText
import com.devd.common.util.LabelText
import com.devd.common.util.RoundedCard

@Composable
fun InputItemInfo(
    productName: String,
    regularPrice: Int,
    purchasePrice: Int,
    updateProductName: (String) -> Unit,
    updateRegularPrice: (Int) -> Unit,
    updatePurchasePrice: (Int) -> Unit
) {
    RoundedCard {
        LabelText(labelIcon = R.drawable.icon_tag, label = "상품명")
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorBlackF8, RoundedCornerShape(10.dp)),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = ColorMainText
            ),
            shape = RoundedCornerShape(10.dp),
            value = productName,
            onValueChange = { updateProductName(it) },
            placeholder = {
                Text(
                    text = "예: 삼다수 2L",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = ColorTertiaryText
                    )
                )
            },
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PriceInputField(
                isActive = false,
                label = "정상가 (선택)",
                value = if (regularPrice == -1) "" else regularPrice.toString()
            ) { updateRegularPrice(it.ifEmpty { "-1" }.toInt()) }
            PriceInputField(
                isActive = true,
                label = "구매가",
                value = if (purchasePrice == -1) "" else purchasePrice.toString()
            ) { updatePurchasePrice(it.ifEmpty { "-1" }.toInt()) }
        }
    }
}

@Composable
fun RowScope.PriceInputField(
    isActive: Boolean,
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.weight(1f)) {
        Text(
            label,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = if (isActive) ColorSecondaryText else ColorTertiaryText
            )
        )
        Spacer(Modifier.height(5.dp))
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                println("Check=>Price newValue=> ${newValue}")
                if (newValue.all { it.isDigit() }) onValueChange(newValue)
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = if (isActive) ColorPrimaryBlue else ColorDisable
            ),
            suffix = {
                Text(
                    "원",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isActive) ColorPrimaryBlue else ColorDisable
                )
            }, // 우측에 '원' 고정
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (isActive) ColorSemiBlue else ColorBlackF8,
                    RoundedCornerShape(10.dp)
                ),
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}