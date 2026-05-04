package com.devd.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devd.database.dao.PriceRecordDao
import com.devd.database.entitiy.PriceRecordEntity
import timber.log.Timber
import java.util.concurrent.Executors

@Database(
    entities = [PriceRecordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MartDatabase : RoomDatabase() {

    abstract fun priceRecordDao(): PriceRecordDao


    companion object {

        fun buildDatabase(context: Context): MartDatabase {
            return Room.databaseBuilder(
                context,
                MartDatabase::class.java,
                "local_mart_db"
            ).setQueryCallback(
                { sqlQuery, bindArgs -> Timber.tag("SQL_LOG").d("$sqlQuery   $bindArgs") },
                Executors.newSingleThreadExecutor()
            ).addMigrations()
                .build()
        }
    }
}