package com.devd.domain.model.report

enum class RangePeriod(val months: Int) {
    ONE_MONTH(1),
    THREE_MONTHS(3),
    SIX_MONTHS(6),
    ALL_TIME(0) // 0일 경우 필터링 없이 전체 조회
}