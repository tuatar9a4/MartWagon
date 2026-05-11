package com.devd.domain.usecase.record

import com.devd.domain.model.datastore.DataStoreKeys
import com.devd.domain.model.report.ChartProductItem
import com.devd.domain.repository.DataStoreRepository
import com.devd.domain.repository.PriceRecordRepository
import java.time.ZonedDateTime
import javax.inject.Inject

class FetchProductNameListUseCase @Inject constructor(
    private val priceRecordRepository: PriceRecordRepository,
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(): List<ChartProductItem> {
        val startTimestamp = ZonedDateTime.now()
            .minusMonths(1)
            .toInstant()
            .toEpochMilli()
        val savedItem = dataStoreRepository.getValue(DataStoreKeys.CHART_PRODUCT_NAME, "")
        var isSelect = savedItem.isEmpty()
        return priceRecordRepository.fetchPriceWithRange(startTimestamp).groupBy { it.productName }
            .mapNotNull {
                if (it.value.size < 2) return@mapNotNull null

                val item = ChartProductItem(
                    productName = it.key,
                    isSelect = if (savedItem.isNotEmpty()) savedItem == it.key else isSelect
                )
                isSelect = false
                item
            }
    }
}