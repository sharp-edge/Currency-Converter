package com.sharpedge.currencyconverter.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: CurrencyHistory)

    @Query("SELECT * FROM currency_records WHERE timestamp >= :since ORDER BY timestamp DESC")
    suspend fun getHistoricalData(since: Long): List<CurrencyHistory>
}