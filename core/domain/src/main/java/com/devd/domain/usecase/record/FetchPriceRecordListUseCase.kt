package com.devd.domain.usecase.record

import com.devd.domain.repository.PriceRecordRepository
import javax.inject.Inject

class FetchPriceRecordListUseCase @Inject constructor(
    private val priceRecordRepository: PriceRecordRepository
) {

    operator fun invoke() = priceRecordRepository.fetchPriceRecordFlow()

}