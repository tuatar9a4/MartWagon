package com.devd.common.ui.register.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
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
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorSecondaryText
import com.devd.common.theme.ColorSemiBlue
import com.devd.common.theme.ColorTertiaryText
import com.devd.common.theme.ColorWhite
import com.devd.common.ui.textfield.BorderTextField
import com.devd.common.util.LabelText
import com.devd.common.util.RoundedCard

@Preview
@Composable
fun InputItemInfoPreview() {
    var regularPrice by remember { mutableLongStateOf(-1) }
    var purchasePrice by remember { mutableLongStateOf(-1) }

    InputItemInfo(
        productName = "삼다수 2L",
        category = "2",
        savedCategoryList = listOf("2", "3"),
        regularPrice = regularPrice,
        purchasePrice = purchasePrice,
        updateProductName = {},
        updateCategory = {},
        addCategory = {},
        updateRegularPrice = { regularPrice = it },
        updatePurchasePrice = { purchasePrice = it }
    )
}

@Composable
fun InputItemInfo(
    productName: String,
    category: String,
    savedCategoryList: List<String>,
    regularPrice: Long,
    purchasePrice: Long,
    updateProductName: (String) -> Unit,
    addCategory: (String) -> Unit,
    updateCategory: (String) -> Unit,
    updateRegularPrice: (Long) -> Unit,
    updatePurchasePrice: (Long) -> Unit
) {
    RoundedCard {
        LabelText(
            labelIcon = R.drawable.icon_tag,
            label = stringResource(R.string.product_name_title)
        )
        BorderTextField(
            modifier = Modifier.fillMaxWidth(),
            value = productName,
            onValueChange = { updateProductName(it) },
            placeHolder = R.string.product_name_placeholder,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
        )
        CategoryInputCard(
            inputCategory = category,
            suggestedCategories = savedCategoryList,
            addCategory = addCategory,
            updateCategory = updateCategory,
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
fun CategoryInputCard(
    inputCategory: String,
    suggestedCategories: List<String>,
    addCategory: (String) -> Unit,
    updateCategory: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorSemiBlue, RoundedCornerShape(10.dp))
            .padding(15.dp)
    ) {
        LabelText(
            labelIcon = R.drawable.icon_hash_tag,
            label = "비교용 카테고리",
            iconColor = ColorPrimaryBlue,
            labelColor = ColorPrimaryBlue
        )
        Spacer(Modifier.height(5.dp))
        BorderTextField(
            value = inputCategory,
            onValueChange = updateCategory,
            placeHolder = R.string.category_placeholder,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        Spacer(Modifier.height(5.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            suggestedCategories.forEach { category ->
                FilterChip(
                    onClick = { updateCategory(category) }, // 칩 클릭 시 텍스트 필드에 입력됨
                    label = {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = ColorWhite,
                        selectedContainerColor = ColorMainText, // 선택 시 어두운 색
                        selectedLabelColor = ColorWhite
                    ),
                    selected = category == inputCategory,
                    shape = RoundedCornerShape(8.dp)
                )
            }
            if (!suggestedCategories.contains(inputCategory) && inputCategory.isNotEmpty()) {
                SuggestionChip(
                    onClick = { addCategory(inputCategory) },
                    label = {
                        Text(
                            text = "+ ${inputCategory} 추가하기",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = ColorPrimaryBlue
                            )
                        )
                    },
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = ColorWhite
                    ),
                    border = SuggestionChipDefaults.suggestionChipBorder(
                        borderColor = ColorSemiBlue,
                        borderWidth = 1.dp,
                        enabled = true
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
            }
        }
        Text(
            text = stringResource(R.string.category_description),
            style = MaterialTheme.typography.labelSmall.copy(
                color = ColorSecondaryText
            )
        )
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
                color = if (isActive) ColorPrimaryBlue else ColorTertiaryText
            )
        )
        Spacer(Modifier.height(5.dp))
        BorderTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = value,
            onValueChange = { newValue ->
                println("Check=>Price newValue=> ${newValue}")
                if (newValue.all { it.isDigit() }) onValueChange(newValue)
            },
            containerColor = if (isActive) ColorSemiBlue else ColorBlackF8,
            suffix = {
                Text(
                    text = stringResource(R.string.currency_unit),
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isActive) ColorPrimaryBlue else ColorTertiaryText
                )
            },
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