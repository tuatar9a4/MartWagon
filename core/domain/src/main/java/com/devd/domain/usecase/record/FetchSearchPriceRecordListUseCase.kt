package com.devd.domain.usecase.record

import com.devd.domain.model.database.PriceRecord
import com.devd.domain.repository.PriceRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchSearchPriceRecordListUseCase @Inject constructor(
    private val priceRecordRepository: PriceRecordRepository
) {

    suspend operator fun invoke(searchWord: String): Flow<List<PriceRecord>> {
        return priceRecordRepository.searchPriceRecordListFlow(searchWord)
    }
}