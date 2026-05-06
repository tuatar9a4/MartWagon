package com.devd.data.di

import com.devd.data.repository.PriceRecordRepositoryImpl
import com.devd.domain.repository.PriceRecordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindPriceRecordRepository(
        priceRecordRepositoryImpl: PriceRecordRepositoryImpl
    ): PriceRecordRepository

}