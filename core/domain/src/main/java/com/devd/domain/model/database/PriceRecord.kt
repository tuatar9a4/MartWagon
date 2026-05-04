package com.devd.domain.model.database

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
    val recordDate: Long, // Presentation에 맞게 포맷팅된 날짜 (예: "2026.05.04")
    val discountRate: Int?  // 매퍼에서 계산된 할인율
) {
    val recordDateStr: String
        get() = run {
            val date = Date(recordDate)
            val format = SimpleDateFormat("yyyy.MM.dd", Locale.US)
            format.format(date)
        }
}