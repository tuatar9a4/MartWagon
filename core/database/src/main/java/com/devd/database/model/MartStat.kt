package com.devd.database.model

import androidx.room.ColumnInfo

// 룸 쿼리 결과 매핑용 데이터 클래스
data class MartStat(
    @ColumnInfo(name = "martName")
    val martName: String,

    @ColumnInfo(name = "count")
    val count: Int
)