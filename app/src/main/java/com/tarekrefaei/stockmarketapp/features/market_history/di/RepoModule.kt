package com.tarekrefaei.stockmarketapp.features.market_history.di

import com.tarekrefaei.stockmarketapp.features.market_history.data.csv.CSVParser
import com.tarekrefaei.stockmarketapp.features.market_history.data.csv.CompanyListingParser
import com.tarekrefaei.stockmarketapp.features.market_history.data.csv.IntraDayInfoParser
import com.tarekrefaei.stockmarketapp.features.market_history.data.repo.StockDataRepoImpl
import com.tarekrefaei.stockmarketapp.features.market_history.domain.model.CompanyListing
import com.tarekrefaei.stockmarketapp.features.market_history.domain.model.IntraDayInfo
import com.tarekrefaei.stockmarketapp.features.market_history.domain.repo.StockDomainRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingParser: CompanyListingParser
    ):CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindIntraDayInfoParser(
        intraDayInfoParser: IntraDayInfoParser
    ):CSVParser<IntraDayInfo>

    @Binds
    @Singleton
    abstract fun bindStockRepo(
        stockDataRepoImpl: StockDataRepoImpl
    ):StockDomainRepoImpl

}