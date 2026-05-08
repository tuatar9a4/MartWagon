package com.devd.domain.usecase.report

import com.devd.domain.model.database.PriceUnit
import com.devd.domain.model.report.FluctuationInfo
import com.devd.domain.model.report.FluctuationReport
import com.devd.domain.model.report.RangePeriod
import com.devd.domain.repository.PriceRecordRepository
import timber.log.Timber
import java.time.ZonedDateTime
import javax.inject.Inject

class GetPriceFluctuationUseCase @Inject constructor(
    private val repository: PriceRecordRepository
) {
    // startDate: 한 달 전, 두 달 전 등의 Timestamp
    suspend operator fun invoke(period: RangePeriod): FluctuationReport {
        // 1. 기간 내 데이터 모두 가져오기
        val records = if (period == RangePeriod.ALL_TIME) repository.fetchPriceRecord()
        else {
            val startTimestamp = ZonedDateTime.now()
                .minusMonths(period.months.toLong())
                .toInstant()
                .toEpochMilli()
            repository.fetchPriceWithRange(startTimestamp)
        }

        // 2. 상품명 단위로 그룹화
        val groupedByProduct = records.groupBy { it.productName }

        val fluctuations = groupedByProduct.mapNotNull { (name, productRecords) ->
            if (productRecords.size < 2) return@mapNotNull null // 비교할 대상이 없으면 제외

            // 시간순 정렬 후 최초와 최신 기록 추출
            val sorted = productRecords.sortedBy { it.recordDate }
            val oldest = sorted.first()
            val newest = sorted.last()

            // 핵심 로직: 용량(quantity)을 고려한 단위 가격 계산 방어 코드
            val oldUnitPrice = calculateUnitPrice(
                unit = PriceUnit.entries[oldest.unit],
                price = oldest.currentPrice,
                quantity = oldest.quantity
            )
            val newUnitPrice = calculateUnitPrice(
                unit = PriceUnit.entries[oldest.unit],
                price = newest.currentPrice,
                quantity = newest.quantity
            )

            if (oldUnitPrice == 0L) return@mapNotNull null

            val rate = (((newUnitPrice - oldUnitPrice.toFloat()) / oldUnitPrice) * 100).toLong()
            Timber.d("Map=> $name ${oldest.currentPrice} => ${newest.currentPrice} | $oldUnitPrice , $newUnitPrice $rate")
            FluctuationInfo(name, oldest.currentPrice, newest.currentPrice, rate)
        }

        // 3. 정렬하여 Top 5 추출
        val surges =
            fluctuations.filter { it.discountRate > 0 }.sortedByDescending { it.discountRate }
                .take(5)
        val plunges =
            fluctuations.filter { it.discountRate < 0 }.sortedBy { it.discountRate }.take(5)

        return FluctuationReport(surges.first(), plunges.first())
    }

    private fun calculateUnitPrice(unit: PriceUnit, price: Long, quantity: Long?): Long {
        val qty =
            if (quantity != null && quantity > 0) quantity.toDouble() else unit.step.toDouble()
        return ((price * unit.step) / qty).toLong()
    }
}