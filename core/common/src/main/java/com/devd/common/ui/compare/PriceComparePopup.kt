package com.devd.common.ui.compare

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devd.common.R
import com.devd.common.theme.ColorBackground
import com.devd.common.theme.ColorDivider
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.theme.ColorWhite
import com.devd.common.ui.compare.model.CompareType
import com.devd.common.ui.compare.screen.CriterionInfo
import com.devd.common.ui.compare.screen.InputCurPrice
import com.devd.common.ui.compare.screen.PriceCompareResult
import com.devd.common.ui.dialog.ShowMessageDialog
import com.devd.domain.model.database.PriceRecord
import com.devd.domain.model.database.PriceUnit

@Preview
@Composable
fun PriceCompareScreenPreview() {
    PriceCompareScreen(
        PriceRecord(
            id = 3199,
            productName = "Esther Roth",
            martName = "Robin Terry",
            currentPrice = 9524,
            originalPrice = 2576,
            memo = "equidem",
            recordDate = 2893,
            discountRate = 50,
            quantity = 0,
            unit = 0
        ),
        {},
        {}
    )
}

@Composable
fun PriceComparePopup(
    criterionPrice: PriceRecord,
    viewModel: PriceCompareViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.shouldBackPage) {
        if (viewModel.shouldBackPage) {
            onDismiss()
            viewModel.shouldBackPage = false
        }
    }

    Dialog(
        onDismissRequest = {
            if (!viewModel.shouldBackPage) onDismiss()
            viewModel.shouldBackPage = false
        },
        properties = DialogProperties(usePlatformDefaultWidth = false) // 전체 화면 설정
    ) {
        PriceCompareScreen(
            priceRecord = criterionPrice,
            onAddNewPrice = viewModel::addNewPrice,
            onCloseClick = onDismiss
        )
    }

    uiState.messageItem?.ShowMessageDialog()
}

@Composable
fun PriceCompareScreen(
    priceRecord: PriceRecord,
    onAddNewPrice: (newPrice: PriceRecord) -> Unit,
    onCloseClick: () -> Unit
) {
    var newPrice by remember { mutableIntStateOf(-1) }
    var newQuantity: Int by remember { mutableIntStateOf(-1) }
    var compareType: CompareType? by remember { mutableStateOf(null) }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(newPrice, newQuantity) {
        if (newPrice == -1 || (newQuantity == -1 && priceRecord.quantity != null)) {
            compareType = null
            return@LaunchedEffect
        }
        val percent =
            if (priceRecord.quantity == null) ((priceRecord.currentPrice - newPrice).toDouble() / priceRecord.currentPrice * 100).toInt()
            else {
                val originPricePerUnit =
                    priceRecord.currentPrice.toFloat() * PriceUnit.entries[priceRecord.unit].step / priceRecord.quantity!!
                val newPricePerUnit =
                    newPrice.toFloat() * PriceUnit.entries[priceRecord.unit].step / newQuantity
                ((originPricePerUnit - newPricePerUnit).toDouble() / originPricePerUnit * 100).toInt()
            }

        compareType = when {
            percent > 10 -> CompareType.VERY_CHEAP
            percent > 0 -> CompareType.CHEAP
            percent == 0 -> CompareType.SAME
            else -> CompareType.EXPENSIVE
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = ColorBackground,
                shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)
            )
            .padding(horizontal = 20.dp)
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            Image(
                modifier = Modifier.clickable(onClick = onCloseClick),
                painter = painterResource(R.drawable.icon_close),
                contentDescription = null,
                colorFilter = ColorFilter.tint(ColorMainText)
            )
            Spacer(Modifier.width(20.dp))
            Image(
                painter = painterResource(R.drawable.icon_balance),
                contentDescription = null,
                colorFilter = ColorFilter.tint(ColorPrimaryBlue)
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = stringResource(R.string.compare_price_title),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = ColorMainText
                )
            )
        }
        Spacer(Modifier.height(20.dp))
        CriterionInfo(
            priceRecord = priceRecord
        )
        Spacer(Modifier.height(10.dp))
        Image(
            modifier = Modifier
                .size(42.dp)
                .shadow(1.dp, CircleShape)
                .border(1.dp, ColorDivider, CircleShape)
                .background(ColorWhite, CircleShape)
                .padding(10.dp),
            painter = painterResource(R.drawable.icon_down_arrow),
            contentDescription = null,
            colorFilter = ColorFilter.tint(ColorPrimaryBlue)
        )
        Spacer(Modifier.height(10.dp))
        InputCurPrice(
            curPrice = newPrice,
            quantity = newQuantity,
            priceUnit = if (priceRecord.quantity == null) null else PriceUnit.entries[priceRecord.unit],
            onValueChange = { newPrice = it },
            onQuantityChange = { newQuantity = it },
            onActionDone = { focusManager.clearFocus() }
        )
        Spacer(Modifier.height(20.dp))
        compareType?.let {
            PriceCompareResult(
                compareType = it,
                onAddNewPrice = { memo ->
                    val newPrice = priceRecord.copy(
                        currentPrice = newPrice,
                        quantity = if (newQuantity != -1) newQuantity else null,
                        memo = memo.ifEmpty { null },
                        recordDate = System.currentTimeMillis()
                    )
                    onAddNewPrice(newPrice)
                }
            )
        }
        Spacer(Modifier.height((30.dp)))
    }
}