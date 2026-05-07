package com.devd.common.ui.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
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
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorPrimaryBlue
import com.devd.common.ui.dialog.ShowMessageDialog
import com.devd.common.ui.register.screen.InputItemInfo
import com.devd.common.ui.register.screen.InputQuantityInfo
import com.devd.common.ui.register.screen.InputStoreInfo
import timber.log.Timber

@Preview
@Composable
fun PriceRegisterScreenPreview() {
    var uiState by remember {
        mutableStateOf(
            PriceRegisterUiState(
                productName = "삼다수 2L",
                regularPrice = 1000,
                purchasePrice = 800,
            )
        )
    }

    PriceRegisterScreen(
        uiState = uiState,
        {}, {}, {
            println("Check=>Price => ${it}")
            uiState = uiState.copy(purchasePrice = it)
        }, {}, {}, {}, {}, {}, {}, {}
    )
}

@Composable
fun PriceRegisterPopup(
    viewModel: PriceRegisterViewModel = hiltViewModel(),
    onDismiss: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.shouldBackPage) {
        Timber.d("CheckDismiss=> LaunchedEffect shouldBackPage ${viewModel.shouldBackPage}")
        if (viewModel.shouldBackPage) onDismiss()
        viewModel.initData()
    }

    Dialog(
        onDismissRequest = {
            Timber.d("CheckDismiss=> onDismissRequest shouldBackPage ${viewModel.shouldBackPage}")
            if (!viewModel.shouldBackPage) onDismiss()
            viewModel.initData()
        },
        properties = DialogProperties(usePlatformDefaultWidth = false) // 전체 화면 설정
    ) {
        PriceRegisterScreen(
            uiState = uiState,
            updateProductName = viewModel::updateProductName,
            updateRegularPrice = viewModel::updateRegularPrice,
            updatePurchasePrice = viewModel::updatePurchasePrice,
            updateSelectStore = viewModel::updateSelectStore,
            updateQuantity = viewModel::updateQuantity,
            updateSelectQuantity = viewModel::updateSelectQuantity,
            addMartItem = viewModel::addMartItem,
            updateMemo = viewModel::updateMemo,
            backPressCall = {
                viewModel.initData()
                onDismiss()
            },
            savePriceRecord = viewModel::validateInputInfo
        )
    }

    uiState.messageItem.ShowMessageDialog()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceRegisterScreen(
    uiState: PriceRegisterUiState,
    updateProductName: (String) -> Unit,
    updateRegularPrice: (Int) -> Unit,
    updatePurchasePrice: (Int) -> Unit,
    updateSelectStore: (Int) -> Unit,
    updateQuantity: (Int) -> Unit,
    updateSelectQuantity: (Int) -> Unit,
    addMartItem: (String) -> Unit,
    updateMemo: (String) -> Unit,
    backPressCall: () -> Unit,
    savePriceRecord: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = ColorBackground,
                    shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Image(
                    modifier = Modifier.clickable(onClick = backPressCall),
                    painter = painterResource(R.drawable.icon_close),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(ColorMainText)
                )
                Text(
                    text = stringResource(R.string.add_new_price_title),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    modifier = Modifier.clickable(onClick = savePriceRecord),
                    text = stringResource(R.string.save),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = ColorPrimaryBlue
                    ),
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 2. 영수증/바코드 스캔 영역 (Dotted Border)
//                ScanSection()
                InputItemInfo(
                    productName = uiState.productName,
                    regularPrice = uiState.regularPrice,
                    purchasePrice = uiState.purchasePrice,
                    updateProductName = updateProductName,
                    updateRegularPrice = updateRegularPrice,
                    updatePurchasePrice = updatePurchasePrice
                )
                InputQuantityInfo(
                    price = uiState.purchasePrice,
                    quantity = uiState.quantity,
                    selectedIndex = uiState.selectQuantityIndex,
                    onQuantityUpdate = updateQuantity,
                    onQuantityUnitUpdate = updateSelectQuantity
                )

                InputStoreInfo(
                    selectStoreIndex = uiState.selectStoreIndex,
                    storeList = uiState.storeList,
                    memo = uiState.infoMemo,
                    onStoreSelected = updateSelectStore,
                    onAddMartList = addMartItem,
                    onMemoUpdate = updateMemo
                )
            }
        }
    }
}

@Composable
fun ScanSection() {
    val stroke =
        Stroke(width = 2f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .drawBehind { // 점선 테두리 그리기
                drawRoundRect(
                    color = Color(0xFFD1D5DB),
                    style = stroke,
                    cornerRadius = CornerRadius(16.dp.toPx())
                )
            }
            .background(Color(0xFFF1F3F5), RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(R.drawable.icon_tag), contentDescription = null)
//            Icon(Icons.Default.PhotoCamera, contentDescription = null, tint = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text("영수증 / 바코드 스캔", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
        }
    }
}