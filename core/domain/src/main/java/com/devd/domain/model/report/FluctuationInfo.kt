package com.devd.domain.model.report

data class FluctuationReport(
    val surgeItems: FluctuationInfo,
    val plungeItems: FluctuationInfo
)

data class FluctuationInfo(
    val itemName: String,
    val originalPrice: Long,
    val currentPrice: Long,
    val discountRate: Long
)
