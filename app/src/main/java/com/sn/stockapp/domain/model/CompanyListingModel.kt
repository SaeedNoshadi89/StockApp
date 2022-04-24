package com.sn.stockapp.domain.model

data class CompanyListingModel(
    val symbol: String,
    val name: String,
    val exchange: String,
    val assetType: String,
    val status: String,
)
