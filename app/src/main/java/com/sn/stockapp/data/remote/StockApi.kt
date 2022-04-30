package com.sn.stockapp.data.remote

import com.sn.stockapp.data.remote.dto.CompanyInfoDto
import com.sn.stockapp.util.API_KEY
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("/query?function=LISTING_STATUS")
    suspend fun getCompanyListing(
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody

    @GET("/query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntradayInfo(
        @Query("symbol") symbol: String,
        @Query("apiKey") apiKey: String = API_KEY,
    ): ResponseBody

    @GET("/query?function=OVERVIEW")
    suspend fun gitCompanyInfo(
        @Query("symbol") symbol: String,
        @Query("apiKey") ApiKey: String = API_KEY,
    ): CompanyInfoDto
}