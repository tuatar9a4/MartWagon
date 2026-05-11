package com.devd.domain.usecase.report

import com.devd.domain.model.report.CategoryReport
import com.devd.domain.repository.PriceRecordRepository
import javax.inject.Inject

class GetMartComparisonUseCase @Inject constructor(
    private val priceRecordRepository: PriceRecordRepository
) {
    suspend operator fun invoke(): List<Pair<String, List<CategoryReport>>> {
        val categories = priceRecordRepository.getAvailableCategories()

        val reportList = mutableListOf<CategoryReport>()

        // 2. 각 카테고리별로 마트 비교 로직 실행
        for (category in categories) {
            val latestPrices = priceRecordRepository.getLatestPricesByCategory(category)

            if (latestPrices.size >= 2) {
                // 단위 가격(100g/ml 등) 기준으로 정렬 로직이 들어갔다고 가정
                val sorted = latestPrices.sortedBy { it.currentPrice } // 실제론 unitPrice로 비교
                val cheapest = sorted.first()
                val expensive = sorted.last()

                reportList.add(
                    CategoryReport(
                        categoryName = category,
                        cheapestMartName = cheapest.martName,
                        cheapestPrice = cheapest.currentPrice, // 단위 가격 노출
                        cheapestPriceForUnit = cheapest.pricePerUnit,
                        savingAmount = expensive.currentPrice - cheapest.currentPrice // 절약 가능 금액
                    )
                )
            }
        }
        val sortMartReport =
            reportList.sortedByDescending { it.savingAmount } // 가격 차이가 큰(절약 효과가 큰) 순서대로 정렬!
        val cheapestGroupPerMart = sortMartReport.groupBy { it.cheapestMartName }
            .toList()
            .sortedByDescending { it.second.size }

        return cheapestGroupPerMart
    }
}