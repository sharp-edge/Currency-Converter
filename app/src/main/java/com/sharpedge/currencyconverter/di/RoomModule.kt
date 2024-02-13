package com.sharpedge.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.sharpedge.currencyconverter.data.database.AppDatabase
import com.sharpedge.currencyconverter.data.database.CurrencyHistoryDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "currency_converter_db"
        ).build()
    }

    @Provides
    fun provideConversionRecordDao(database: AppDatabase): CurrencyHistoryDao {
        return database.currencyHistoryDao()
    }
}
