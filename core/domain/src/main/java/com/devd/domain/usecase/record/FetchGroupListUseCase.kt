package com.devd.domain.usecase.record

import com.devd.domain.model.database.PriceRecord
import com.devd.domain.model.group.GroupType
import com.devd.domain.model.group.GroupType.CATEGORY
import com.devd.domain.model.group.GroupType.MART
import com.devd.domain.repository.PriceRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchGroupListUseCase @Inject constructor(
    private val priceRecordRepository: PriceRecordRepository
) {

    operator fun invoke(groupType: GroupType, itemName: String): Flow<List<PriceRecord>> {
        return when (groupType) {
            MART -> priceRecordRepository.fetchRecordsByMart(itemName)
            CATEGORY -> priceRecordRepository.fetchRecordsByCategory(itemName)
        }
    }

}