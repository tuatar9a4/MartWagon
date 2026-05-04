package com.devd.database.di

import android.content.Context
import com.devd.database.MartDatabase
import com.devd.database.dao.PriceRecordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDiaryDatabase(@ApplicationContext context: Context): MartDatabase =
        MartDatabase.buildDatabase(context)


    @Provides
    fun providePriceRecordDao(database: MartDatabase): PriceRecordDao = database.priceRecordDao()

}
