package com.devd.data

import com.devd.database.entitiy.PriceRecordEntity
import com.devd.domain.model.database.PriceRecord

fun PriceRecordEntity.toDomain(): PriceRecord {
    val calculatedDiscount = originalPrice?.let {
        ((it - currentPrice).toDouble() / it * 100).toInt()
    }

    return PriceRecord(
        id = this.id,
        productName = this.productName,
        martName = this.martName,
        currentPrice = this.currentPrice,
        originalPrice = this.originalPrice,
        memo = this.memo,
        recordDate = this.recordTimestamp,
        quantity = this.quantity,
        unit = this.unit,
        discountRate = calculatedDiscount
    )
}

fun PriceRecord.toEntity(): PriceRecordEntity {

    return PriceRecordEntity(
        productName = this.productName,
        martName = this.martName,
        currentPrice = this.currentPrice,
        originalPrice = this.originalPrice,
        memo = this.memo,
        recordTimestamp = this.recordDate,
        quantity = this.quantity,
        unit = this.unit
    )
}