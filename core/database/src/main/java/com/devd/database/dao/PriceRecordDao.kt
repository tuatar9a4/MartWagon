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

    // 1. 홈 탭 (기록장): 전체 리스트를 최신순으로 가져오기
    @Query("SELECT * FROM price_records ORDER BY record_timestamp DESC")
    fun getAllRecords(): Flow<List<PriceRecordEntity>>

    // 2. 검색 화면: 상품명 또는 마트명에 검색어가 포함된 항목 찾기
    @Query("""
        SELECT * FROM price_records 
        WHERE product_name LIKE '%' || :searchQuery || '%' 
        OR mart_name LIKE '%' || :searchQuery || '%' 
        ORDER BY record_timestamp DESC
    """)
    fun searchRecordsFlow(searchQuery: String): Flow<List<PriceRecordEntity>>

    @Query("""
        SELECT * FROM price_records 
        WHERE product_name LIKE '%' || :searchQuery || '%' 
        OR mart_name LIKE '%' || :searchQuery || '%' 
        ORDER BY record_timestamp DESC
    """)
    suspend fun searchRecords(searchQuery: String): List<PriceRecordEntity>

    // 3. 비교하기 화면: 특정 상품의 과거 가격 기록 히스토리 가져오기
    // (현재 등록하려는 상품명과 똑같은 과거 기록들을 찾아 가격 변동을 계산할 때 사용)
    @Query("SELECT * FROM price_records WHERE product_name = :productName ORDER BY record_timestamp DESC")
    fun getProductHistory(productName: String): Flow<List<PriceRecordEntity>>

    // 4. 마트 상세 화면: 특정 마트의 기록만 모아보기
    @Query("SELECT * FROM price_records WHERE mart_name = :martName ORDER BY record_timestamp DESC")
    fun getRecordsByMart(martName: String): Flow<List<PriceRecordEntity>>

    // 5. 태그 상세 화면: 메모(memo)에 특정 태그(예: "#우유")가 포함된 기록 모아보기
    @Query("SELECT * FROM price_records WHERE memo LIKE '%' || :tag || '%' ORDER BY record_timestamp DESC")
    fun getRecordsByTag(tag: String): Flow<List<PriceRecordEntity>>

    // ==============================================
    // 통계 및 집계 (Aggregation)
    // ==============================================

    // 6. 마트별/태그별 탭 (폴더 뷰): 각 마트당 기록된 상품 개수를 카운트해서 내림차순 정렬
    @Query("SELECT mart_name as martName, COUNT(*) as count FROM price_records GROUP BY mart_name ORDER BY count DESC")
    fun getMartStats(): Flow<List<MartStat>>

    @Query("DELETE FROM price_records")
    suspend fun deleteAllRecords()
}