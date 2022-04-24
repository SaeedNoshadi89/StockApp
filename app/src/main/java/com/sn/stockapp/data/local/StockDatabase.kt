package com.sn.stockapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sn.stockapp.data.local.entity.CompanyListingEntity

@Database(
    entities = [CompanyListingEntity::class],
    version = 1,
    exportSchema = false
)
abstract class StockDatabase: RoomDatabase() {
    abstract val stockDao: StockDao
}