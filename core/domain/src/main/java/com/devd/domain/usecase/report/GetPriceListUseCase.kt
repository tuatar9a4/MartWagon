package com.devd.domain.usecase.report

import com.devd.domain.model.datastore.DataStoreKeys
import com.devd.domain.model.report.SimplePriceInfo
import com.devd.domain.repository.DataStoreRepository
import com.devd.domain.repository.PriceRecordRepository
import javax.inject.Inject

class GetPriceListUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val priceRecordRepository: PriceRecordRepository
) {

    suspend operator fun invoke(productName: String?): List<SimplePriceInfo> {
        if (productName != null)
            dataStoreRepository.saveValue(DataStoreKeys.CHART_PRODUCT_NAME, productName)

        val requestName = dataStoreRepository.getValue(DataStoreKeys.CHART_PRODUCT_NAME, "")
            .takeIf { it.isNotEmpty() } ?: (priceRecordRepository.fetchPriceRecord()
            .firstOrNull()?.productName ?: return emptyList())

        return priceRecordRepository.fetchSimplePriceList(requestName)
    }
}