package com.sn.stockapp.domain.repository

import com.sn.stockapp.domain.model.CompanyInfoModel
import com.sn.stockapp.domain.model.CompanyListingModel
import com.sn.stockapp.domain.model.IntradayInfoModel
import com.sn.stockapp.util.Result
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun companyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Result<List<CompanyListingModel>>>

    suspend fun getCompanyInfo(symbol: String): Result<CompanyInfoModel>
    suspend fun getIntradayInfo(symbol: String) : Result<List<IntradayInfoModel>>
}