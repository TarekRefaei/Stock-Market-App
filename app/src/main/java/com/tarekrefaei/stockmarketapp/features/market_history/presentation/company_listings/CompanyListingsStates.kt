package com.tarekrefaei.stockmarketapp.features.market_history.presentation.company_listings

import com.tarekrefaei.stockmarketapp.features.market_history.domain.model.CompanyListing

data class CompanyListingsStates(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
