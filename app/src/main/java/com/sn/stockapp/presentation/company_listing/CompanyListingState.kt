package com.sn.stockapp.presentation.company_listing

import com.sn.stockapp.domain.model.CompanyListingModel

data class CompanyListingState(
    val companies: List<CompanyListingModel> = emptyList(),
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val searchQuery: String = "",
    val error: String = ""
)
