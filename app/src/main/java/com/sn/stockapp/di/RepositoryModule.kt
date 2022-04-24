package com.sn.stockapp.di

import com.sn.stockapp.data.csv.CSVParser
import com.sn.stockapp.data.csv.CompanyListingParser
import com.sn.stockapp.data.repository.StockRepositoryImpl
import com.sn.stockapp.domain.model.CompanyListingModel
import com.sn.stockapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingParser(
        companyListingParser: CompanyListingParser
    ): CSVParser<CompanyListingModel>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}