package com.devd.domain.model.database

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class PriceRecord(
    val id: Long,
    val productName: String,
    val martName: String,
    val currentPrice: Int,
    val originalPrice: Int?,
    val memo: String?,
    val recordDate: Long,
    val quantity: Int?,
    val unit: Int,
    val discountRate: Int?  // 매퍼에서 계산된 할인율
) {
    val recordDateStr: String  // Presentation에 맞게 포맷팅된 날짜 (예: "2026.05.04")
        get() = run {
            val date = Date(recordDate)
            val format = SimpleDateFormat("yyyy.MM.dd", Locale.US)
            format.format(date)
        }

    val originalPriceStr: String?
        get() = run {
            originalPrice ?: return@run null
            val formatter = DecimalFormat("#,###")
            formatter.format(originalPrice)
        }

    val currentPriceStr: String
        get() = run {
            val formatter = DecimalFormat("#,###")
            formatter.format(currentPrice)
        }

    val unitPerStr: String
        @Composable
        get() = run {
            val selectUnit = PriceUnit.entries[unit]
            "${selectUnit.step}${stringResource(selectUnit.display)}"
        }

    val unitPerPrice: String?
        get() = run {
            quantity?.takeIf { it != -1 && it != 0 } ?: return@run null
            val formatter = DecimalFormat("#,###")
            val perPrice = currentPrice.toFloat() * PriceUnit.entries[unit].step / quantity
            formatter.format(perPrice.toInt())
        }
}