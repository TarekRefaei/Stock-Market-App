package com.tarekrefaei.stockmarketapp.features.market_history.domain.model

data class CompanyListing(
    val name: String,
    val symbol: String,
    val exchange: String,
)