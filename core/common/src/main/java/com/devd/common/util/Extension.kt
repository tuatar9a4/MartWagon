package com.devd.common.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

fun Long.getDaysAgo(): Long {
    // 1. 타임스탬프를 현재 시스템 기본 시간대의 LocalDate로 변환
    val targetDate = Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    // 2. 오늘의 LocalDate 가져오기
    val today = LocalDate.now()

    // 3. 두 날짜 사이의 일수(Days) 차이 계산
    return ChronoUnit.DAYS.between(targetDate, today)
}