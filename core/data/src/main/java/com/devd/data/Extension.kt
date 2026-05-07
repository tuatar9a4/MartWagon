package com.devd.data

import com.devd.database.entitiy.PriceRecordEntity
import com.devd.domain.model.database.PriceRecord

fun PriceRecordEntity.toDomain(): PriceRecord {
    val calculatedDiscount = originalPrice?.let {
        if (it > currentPrice) ((it - currentPrice).toDouble() / it * 100).toInt()
        else null
    }

    return PriceRecord(
        id = this.id,
        productName = this.productName,
        martName = this.martName,
        currentPrice = this.currentPrice,
        originalPrice = this.originalPrice,
        memo = this.memo,
        recordDate = this.recordTimestamp,
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
        recordTimestamp = this.recordDate
    )
}