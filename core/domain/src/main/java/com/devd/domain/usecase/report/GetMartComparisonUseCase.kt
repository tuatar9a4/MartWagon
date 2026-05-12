package com.devd.domain.usecase.report

import com.devd.domain.model.report.CategoryReport
import com.devd.domain.repository.PriceRecordRepository
import timber.log.Timber
import javax.inject.Inject

class GetMartComparisonUseCase @Inject constructor(
    private val priceRecordRepository: PriceRecordRepository
) {
    suspend operator fun invoke(): List<Pair<String, List<CategoryReport>>> {
        val categories = priceRecordRepository.getAvailableCategories()

        val reportList = mutableListOf<CategoryReport>()

        for (category in categories) {
            val latestPrices = priceRecordRepository.getLatestPricesByCategory(category)

            Timber.d("Category[$category]=> ${latestPrices}")
            if (latestPrices.size >= 2) {
                val sorted = latestPrices.sortedBy { it.currentPrice }
                val cheapest = sorted.first()
                val expensive = sorted.last()

                reportList.add(
                    CategoryReport(
                        categoryName = category,
                        cheapestMartName = cheapest.martName,
                        cheapestPrice = cheapest.currentPrice,
                        cheapestPriceForUnit = cheapest.pricePerUnit,
                        savingAmount = expensive.currentPrice - cheapest.currentPrice
                    )
                )
            }
        }
        val sortMartReport =
            reportList.sortedByDescending { it.savingAmount }
        val cheapestGroupPerMart = sortMartReport.groupBy { it.cheapestMartName }
            .toList()
            .sortedByDescending { it.second.size }

        return cheapestGroupPerMart
    }
}