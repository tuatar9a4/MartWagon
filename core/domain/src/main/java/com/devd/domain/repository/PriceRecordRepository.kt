package com.devd.domain.repository

import com.devd.domain.model.database.PriceRecord
import kotlinx.coroutines.flow.Flow

interface PriceRecordRepository {
    suspend fun savePriceRecord(priceRecord: PriceRecord): PriceRecord?
    fun fetchPriceRecordFlow(): Flow<List<PriceRecord>>
    suspend fun fetchPriceRecord(): List<PriceRecord>
    suspend fun fetchPriceWithRange(sineTime: Long): List<PriceRecord>
    suspend fun searchPriceRecordList(searchWord: String): Flow<List<PriceRecord>>
    suspend fun deleteItemWithId(id: Long)
    suspend fun tempDeleteAllItem()
}