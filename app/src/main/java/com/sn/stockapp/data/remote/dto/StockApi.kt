package com.sn.stockapp.data.remote.dto

import com.sn.stockapp.util.API_KEY
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("/query?function=LISTING_STATUS")
    suspend fun getCompanyListing(
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody
}