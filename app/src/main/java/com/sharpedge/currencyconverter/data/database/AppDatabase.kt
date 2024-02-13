package com.sharpedge.currencyconverter.data.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [CurrencyHistory::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyHistoryDao(): CurrencyHistoryDao
}
