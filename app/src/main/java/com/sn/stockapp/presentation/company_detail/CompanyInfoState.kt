package com.sn.stockapp.presentation.company_detail

import com.sn.stockapp.domain.model.CompanyInfoModel
import com.sn.stockapp.domain.model.IntradayInfoModel

data class CompanyInfoState(
    val stockInfo: List<IntradayInfoModel>? = emptyList(),
    val company: CompanyInfoModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
