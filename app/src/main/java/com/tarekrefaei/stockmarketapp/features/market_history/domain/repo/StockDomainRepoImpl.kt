package com.tarekrefaei.stockmarketapp.features.market_history.domain.repo

import com.tarekrefaei.stockmarketapp.features.market_history.domain.model.CompanyInfo
import com.tarekrefaei.stockmarketapp.features.market_history.domain.model.CompanyListing
import com.tarekrefaei.stockmarketapp.features.market_history.domain.model.IntraDayInfo
import com.tarekrefaei.stockmarketapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface StockDomainRepoImpl {

    suspend fun getCompanyList(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntraDayInfo(
        symbol: String
    ): Resource<List<IntraDayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ) : Resource<CompanyInfo>

}