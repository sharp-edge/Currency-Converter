package com.sharpedge.currencyconverter.di

import com.sharpedge.currencyconverter.usecase.api.GetConversionRatesUseCase
import com.sharpedge.currencyconverter.usecase.api.GetCurrencySymbolsUseCase
import com.sharpedge.currencyconverter.usecase.api.IGetConversionRatesUseCase
import com.sharpedge.currencyconverter.usecase.api.IGetCurrencySymbolsUseCase
import com.sharpedge.currencyconverter.usecase.database.GetHistoricalDataUseCase
import com.sharpedge.currencyconverter.usecase.database.IGetHistoricalDataUseCase
import com.sharpedge.currencyconverter.usecase.database.ISaveRecordUseCase
import com.sharpedge.currencyconverter.usecase.database.SaveRecordUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetCurrencySymbolsUseCase(
        impl: GetCurrencySymbolsUseCase
    ): IGetCurrencySymbolsUseCase

    @Binds
    abstract fun bindGetConversionRatesUseCase(
        impl: GetConversionRatesUseCase
    ): IGetConversionRatesUseCase

    @Binds
    abstract fun bindSaveRecordUseCase(
        impl: SaveRecordUseCase
    ): ISaveRecordUseCase

    // Add bindings for GetHistoricalDataUseCase
    @Binds
    abstract fun bindGetHistoricalDataUseCase(
        impl: GetHistoricalDataUseCase
    ): IGetHistoricalDataUseCase

}