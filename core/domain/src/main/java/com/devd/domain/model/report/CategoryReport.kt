package com.devd.domain.model.report

data class CategoryReport(
    val categoryName: String,
    val cheapestMartName: String,
    val cheapestPrice: Long, // 실 가격 노출
    val cheapestPriceForUnit: String, // 단위 가격 노출
    val savingAmount: Long // 절약 가능 금액
)