package com.tarekrefaei.stockmarketapp.features.market_history.data.mapper

import com.tarekrefaei.stockmarketapp.features.market_history.data.local.CompanyListingEntity
import com.tarekrefaei.stockmarketapp.features.market_history.data.remote.dto.CompanyInfoDto
import com.tarekrefaei.stockmarketapp.features.market_history.domain.model.CompanyInfo
import com.tarekrefaei.stockmarketapp.features.market_history.domain.model.CompanyListing


fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}