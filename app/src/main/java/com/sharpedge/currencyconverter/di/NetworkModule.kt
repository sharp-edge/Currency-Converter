package com.sharpedge.currencyconverter.di

import com.google.gson.Gson
import com.sharpedge.currencyconverter.data.database.CurrencyHistoryDao
import com.sharpedge.currencyconverter.network.RequestInterceptor
import com.sharpedge.currencyconverter.network.api.CurrencyService
import com.sharpedge.currencyconverter.repository.CurrencyRepository
import com.sharpedge.currencyconverter.repository.ICurrencyRepository
import com.sharpedge.currencyconverter.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) = Retrofit
        .Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideOkHttpClient(requestInterceptor: RequestInterceptor, loggingInterceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .readTimeout(60,TimeUnit.SECONDS)
        .connectTimeout(60,TimeUnit.SECONDS)
        .addInterceptor(requestInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    @Singleton
    @Provides
    fun providesLoggingInterceptor() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideAPIService(retrofit: Retrofit) = retrofit.create(CurrencyService::class.java)


    @Singleton
    @Provides
    fun provideCurrencyRepository(repo: CurrencyService, currencyHistoryDao: CurrencyHistoryDao) : ICurrencyRepository {
        return CurrencyRepository(repo, provideGson(), currencyHistoryDao)
    }
}