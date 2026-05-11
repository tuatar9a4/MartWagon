package com.devd.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devd.database.entitiy.PriceRecordEntity
import com.devd.database.model.MartStat
import kotlinx.coroutines.flow.Flow

@Dao
interface PriceRecordDao {

    // ==============================================
    // 쓰기 작업 (CUD) - Coroutine suspend 함수 사용
    // ==============================================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: PriceRecordEntity): Long

    @Delete
    suspend fun deleteRecord(record: PriceRecordEntity)

    // 특정 기록 ID로 삭제하기
    @Query("DELETE FROM price_records WHERE record_id = :recordId")
    suspend fun deleteRecordById(recordId: Long)


    // ==============================================
    // 읽기 작업 (Read) - Flow 반환으로 실시간 UI 업데이트
    // ==============================================

    @Query("SELECT * FROM price_records ORDER BY record_timestamp DESC")
    fun getAllRecordsFlow(): Flow<List<PriceRecordEntity>>

    @Query("SELECT * FROM price_records ORDER BY record_timestamp DESC")
    suspend fun getAllRecords(): List<PriceRecordEntity>

    @Query(
        """
        SELECT * FROM price_records 
        WHERE product_name LIKE '%' || :searchQuery || '%' 
        OR mart_name LIKE '%' || :searchQuery || '%' 
        ORDER BY record_timestamp DESC
    """
    )
    fun searchRecordsFlow(searchQuery: String): Flow<List<PriceRecordEntity>>

    @Query(
        """
        SELECT * FROM price_records 
        WHERE product_name LIKE '%' || :searchQuery || '%' 
        OR mart_name LIKE '%' || :searchQuery || '%' 
        ORDER BY record_timestamp DESC
    """
    )
    suspend fun searchRecords(searchQuery: String): List<PriceRecordEntity>

    @Query(
        """
        SELECT * FROM price_records 
        WHERE record_timestamp >= :startTimestamp 
        ORDER BY record_timestamp DESC
    """
    )
    suspend fun getRecordsSince(startTimestamp: Long): List<PriceRecordEntity>

    @Query("SELECT DISTINCT category FROM price_records WHERE category IS NOT NULL AND category != '' ORDER BY category ASC")
    suspend fun getAvailableCategories(): List<String>

    @Query(
        """
    SELECT * FROM price_records 
    WHERE category = :category 
    AND record_id IN (
        SELECT record_id 
        FROM price_records 
        WHERE category = :category
        GROUP BY mart_name, product_name 
        HAVING MAX(record_timestamp)
    )
    """
    )
    suspend fun getLatestPricesByCategory(category: String): List<PriceRecordEntity>

    @Query("SELECT * FROM price_records WHERE product_name = :productName ORDER BY record_timestamp DESC")
    fun getProductHistory(productName: String): Flow<List<PriceRecordEntity>>

    @Query("SELECT * FROM price_records WHERE mart_name = :martName ORDER BY record_timestamp DESC")
    fun getRecordsByMart(martName: String): Flow<List<PriceRecordEntity>>

    @Query("SELECT * FROM price_records WHERE category LIKE '%' || :categoryName || '%' ORDER BY record_timestamp DESC")
    fun getRecordsByCategory(categoryName: String): Flow<List<PriceRecordEntity>>

    // ==============================================
    // 통계 및 집계 (Aggregation)
    // ==============================================

    // 6. 마트별/태그별 탭 (폴더 뷰): 각 마트당 기록된 상품 개수를 카운트해서 내림차순 정렬
    @Query("SELECT mart_name as martName, COUNT(*) as count FROM price_records GROUP BY mart_name ORDER BY count DESC")
    fun getMartStats(): Flow<List<MartStat>>

    @Query("DELETE FROM price_records")
    suspend fun deleteAllRecords()
}