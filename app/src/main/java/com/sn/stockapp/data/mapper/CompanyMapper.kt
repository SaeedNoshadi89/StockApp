package com.sn.stockapp.data.mapper

import com.sn.stockapp.data.local.entity.CompanyListingEntity
import com.sn.stockapp.data.remote.dto.CompanyInfoDto
import com.sn.stockapp.domain.model.CompanyInfoModel
import com.sn.stockapp.domain.model.CompanyListingModel

fun CompanyListingEntity.toCompanyListingModel(): CompanyListingModel {
    return CompanyListingModel(
        symbol = symbol,
        name = name,
        exchange = exchange,
        assetType = assetType,
        status = status
    )
}

fun CompanyListingModel.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        symbol = symbol,
        name = name,
        exchange = exchange,
        assetType = assetType,
        status = status
    )
}

fun CompanyInfoDto.toCompanyInfoModel(): CompanyInfoModel{
    return CompanyInfoModel(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}