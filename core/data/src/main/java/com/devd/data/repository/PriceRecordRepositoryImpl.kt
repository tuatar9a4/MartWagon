package com.devd.data.repository

import com.devd.data.toDomain
import com.devd.data.toEntity
import com.devd.database.dao.PriceRecordDao
import com.devd.domain.model.database.PriceRecord
import com.devd.domain.repository.PriceRecordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PriceRecordRepositoryImpl @Inject constructor(
    private val priceRecordDao: PriceRecordDao
) : PriceRecordRepository {

    override suspend fun savePriceRecord(priceRecord: PriceRecord): PriceRecord? {
        val result = priceRecordDao.insertRecord(priceRecord.toEntity())
        return if (result == -1L) null else priceRecord
    }

    override suspend fun fetchPriceRecord(): List<PriceRecord> {
        return priceRecordDao.getAllRecords().map { it.toDomain() }
    }

    override fun fetchPriceRecordFlow(): Flow<List<PriceRecord>> {
        return priceRecordDao.getAllRecordsFlow().map { it.map { it.toDomain() } }
    }

    override suspend fun fetchPriceWithRange(sineTime: Long): List<PriceRecord> {
        return priceRecordDao.getRecordsSince(sineTime).map { it.toDomain() }
    }

    override suspend fun getAvailableCategories(): List<String> {
        return priceRecordDao.getAvailableCategories()
    }

    override suspend fun getLatestPricesByCategory(category : String): List<PriceRecord> {
        return priceRecordDao.getLatestPricesByCategory(category).map { it.toDomain() }
    }

    override suspend fun searchPriceRecordList(searchWord: String): Flow<List<PriceRecord>> {
        return priceRecordDao.searchRecordsFlow(searchWord).map { it.map { it.toDomain() } }
    }

    override suspend fun deleteItemWithId(id: Long) {
        priceRecordDao.deleteRecordById(id)
    }

    override suspend fun tempDeleteAllItem() {
        priceRecordDao.deleteAllRecords()
    }

}
