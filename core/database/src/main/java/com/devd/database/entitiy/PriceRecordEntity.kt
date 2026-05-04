package com.devd.database.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "price_records")
data class PriceRecordEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "record_id")
    val id: Long = 0L,

    @ColumnInfo(name = "product_name")
    val productName: String,

    @ColumnInfo(name = "mart_name")
    val martName: String,

    @ColumnInfo(name = "current_price")
    val currentPrice: Int,

    // 정상가는 입력 안 할 수도 있으니 Nullable 처리
    @ColumnInfo(name = "original_price")
    val originalPrice: Int? = null,

    // 메모도 선택 사항이므로 Nullable
    @ColumnInfo(name = "memo")
    val memo: String? = null,

    // 날짜는 String보다 Long(Unix Timestamp)으로 저장해야 나중에 정렬/비교 쿼리 시 압도적으로 유리합니다.
    @ColumnInfo(name = "record_timestamp")
    val recordTimestamp: Long
)
