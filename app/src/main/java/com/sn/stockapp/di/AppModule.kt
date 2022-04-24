package com.sn.stockapp.di

import android.app.Application
import androidx.room.Room
import com.sn.stockapp.data.local.StockDatabase
import com.sn.stockapp.data.remote.dto.StockApi
import com.sn.stockapp.util.BASE_URL
import com.sn.stockapp.util.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(): StockApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()


    @Provides
    @Singleton
    fun provideStokeDb(app: Application): StockDatabase =
        Room.databaseBuilder(
            app,
            StockDatabase::class.java,
            DB_NAME
        ).build()

}