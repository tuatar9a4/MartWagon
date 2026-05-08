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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
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

@Preview
@Composable
fun InputItemInfoPreview() {
    var regularPrice by remember { mutableLongStateOf(-1) }
    var purchasePrice by remember { mutableLongStateOf(-1) }

    InputItemInfo(
        productName = "삼다수 2L",
        regularPrice = regularPrice,
        purchasePrice = purchasePrice,
        updateProductName = {},
        updateRegularPrice = { regularPrice = it },
        updatePurchasePrice = { purchasePrice = it }
    )
}

@Composable
fun InputItemInfo(
    productName: String,
    regularPrice: Long,
    purchasePrice: Long,
    updateProductName: (String) -> Unit,
    updateRegularPrice: (Long) -> Unit,
    updatePurchasePrice: (Long) -> Unit
) {
    RoundedCard {
        LabelText(
            labelIcon = R.drawable.icon_tag,
            label = stringResource(R.string.product_name_title)
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorBlackF8, RoundedCornerShape(10.dp)),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = ColorMainText
            ),
            shape = RoundedCornerShape(10.dp),
            value = productName,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            onValueChange = { updateProductName(it) },
            placeholder = {
                Text(
                    text = stringResource(R.string.product_name_placeholder),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = ColorTertiaryText
                    )
                )
            },
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PriceInputField(
                isActive = false,
                label = stringResource(R.string.option_original_price),
                value = if (regularPrice == -1L) "" else regularPrice.toString()
            ) { updateRegularPrice(it.ifEmpty { "-1" }.toLong()) }
            PriceInputField(
                isActive = true,
                label = stringResource(R.string.option_current_price),
                value = if (purchasePrice == -1L) "" else purchasePrice.toString()
            ) { updatePurchasePrice(it.ifEmpty { "-1" }.toLong()) }
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
    val focusManager = LocalFocusManager.current
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
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = if (isActive) ColorPrimaryBlue else ColorDisable
            ),
            suffix = {
                Text(
                    text = stringResource(R.string.currency_unit),
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isActive) ColorPrimaryBlue else ColorDisable
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (isActive) ColorSemiBlue else ColorBlackF8,
                    RoundedCornerShape(10.dp)
                ),
            shape = RoundedCornerShape(10.dp),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = if (isActive) ImeAction.Done else ImeAction.Next
            )
        )
    }
}