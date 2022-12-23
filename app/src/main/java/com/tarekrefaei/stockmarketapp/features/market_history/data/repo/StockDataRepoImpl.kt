package com.tarekrefaei.stockmarketapp.features.market_history.data.repo

import android.os.Build
import androidx.annotation.RequiresApi
import com.tarekrefaei.stockmarketapp.features.market_history.data.csv.CompanyListingParser
import com.tarekrefaei.stockmarketapp.features.market_history.data.csv.IntraDayInfoParser
import com.tarekrefaei.stockmarketapp.features.market_history.data.local.StockDatabase
import com.tarekrefaei.stockmarketapp.features.market_history.data.mapper.toCompanyInfo
import com.tarekrefaei.stockmarketapp.features.market_history.data.mapper.toCompanyListing
import com.tarekrefaei.stockmarketapp.features.market_history.data.mapper.toCompanyListingEntity
import com.tarekrefaei.stockmarketapp.features.market_history.data.remote.StockApi
import com.tarekrefaei.stockmarketapp.features.market_history.domain.model.CompanyInfo
import com.tarekrefaei.stockmarketapp.features.market_history.domain.model.CompanyListing
import com.tarekrefaei.stockmarketapp.features.market_history.domain.model.IntraDayInfo
import com.tarekrefaei.stockmarketapp.features.market_history.domain.repo.StockDomainRepoImpl
import com.tarekrefaei.stockmarketapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class StockDataRepoImpl @Inject constructor(
    private val api: StockApi,
    db: StockDatabase,
    private val companyListingParser: CompanyListingParser,
    private val intraDayInfoParser: IntraDayInfoParser
) : StockDomainRepoImpl {

    private val dao = db.dao

    override suspend fun getCompanyList(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            //For Search query
            val localListing = dao.searchCompanyListing(query)
            emit(
                Resource.Success(
                    data = localListing.map {
                        it.toCompanyListing()
                    }
                )
            )
            //to be sure that there is no search query
            val isDbEmpty = localListing.isEmpty() && query.isBlank()
            val shouldLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            //This to get List From API
            val remoteListing = try {
                val response = api.getListings()
                companyListingParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message.toString()))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message.toString()))
                null
            }
            //Convert every item to DB
            remoteListing?.let { listing ->
                emit(Resource.Loading(true))
                dao.clearCompanyListing()
                dao.insertCompanyListing(
                    listing.map {
                        it.toCompanyListingEntity()
                    }
                )
                emit(
                    Resource.Success(
                        data = dao
                            .searchCompanyListing("")
                            .map {
                                it.toCompanyListing()
                            }
                    )
                )
                emit(
                    Resource.Loading(false)
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getIntraDayInfo(symbol: String): Resource<List<IntraDayInfo>> {
        return try {
            val response = api.getIntraDayInfo(symbol)
            val results = intraDayInfoParser.parse(response.byteStream())
            Resource.Success(results)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(message = e.message.toString())
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(message = e.message.toString())
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val result = api.getCompanyInfo(symbol)
            Resource.Success(result.toCompanyInfo())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(message = e.message.toString())
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(message = e.message.toString())
        }
    }
}