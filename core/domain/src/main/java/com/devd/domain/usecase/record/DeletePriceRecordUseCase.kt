package com.devd.domain.usecase.record

import com.devd.domain.repository.PriceRecordRepository
import javax.inject.Inject

class DeletePriceRecordUseCase @Inject constructor(
    private val priceRecordRepository: PriceRecordRepository
) {
    suspend operator fun invoke(deleteId: Long) {
        priceRecordRepository.deleteItemWithId(deleteId)
    }
}