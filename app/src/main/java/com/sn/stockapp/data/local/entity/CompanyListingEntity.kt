package com.sn.stockapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyListingEntity(
    @PrimaryKey val id: Int? = null,
    val symbol: String,
    val name: String,
    val exchange: String,
    val assetType: String,
    val status: String,
)
