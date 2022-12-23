package com.tarekrefaei.stockmarketapp.features.market_history.presentation.company_listings

sealed class CompanyListingsEvents {
    object Refresh : CompanyListingsEvents()
    data class OnSearchQueryChange(val query: String) : CompanyListingsEvents()
}
