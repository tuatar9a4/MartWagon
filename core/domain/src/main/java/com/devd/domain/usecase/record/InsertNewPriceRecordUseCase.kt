package com.devd.domain.usecase.record

import com.devd.domain.model.database.PriceRecord
import com.devd.domain.repository.PriceRecordRepository
import javax.inject.Inject

class InsertNewPriceRecordUseCase @Inject constructor(
    private val priceRecordRepository: PriceRecordRepository
) {

    suspend operator fun invoke(priceRecord: PriceRecord): PriceRecord? {
        return priceRecordRepository.savePriceRecord(priceRecord)
    }
}