package com.devd.domain.repository

import com.devd.domain.model.database.PriceRecord
import com.devd.domain.model.report.SimplePriceInfo
import kotlinx.coroutines.flow.Flow

interface PriceRecordRepository {
    suspend fun savePriceRecord(priceRecord: PriceRecord): PriceRecord?
    fun fetchPriceRecordFlow(): Flow<List<PriceRecord>>
    suspend fun fetchPriceRecord(): List<PriceRecord>
    suspend fun fetchPriceWithRange(sineTime: Long): List<PriceRecord>
    fun fetchRecordsByMart(martName: String): Flow<List<PriceRecord>>
    fun fetchRecordsByCategory(categoryName: String): Flow<List<PriceRecord>>
    suspend fun getAvailableCategories(): List<String>
    suspend fun getLatestPricesByCategory(category: String): List<PriceRecord>
    suspend fun searchPriceRecordListFlow(searchWord: String): Flow<List<PriceRecord>>
    suspend fun fetchSimplePriceList(product: String): List<SimplePriceInfo>
    suspend fun deleteItemWithId(id: Long)
    suspend fun tempDeleteAllItem()
}