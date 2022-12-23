package com.tarekrefaei.stockmarketapp.features.market_history.presentation.company_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarekrefaei.stockmarketapp.features.market_history.domain.repo.StockDomainRepoImpl
import com.tarekrefaei.stockmarketapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CompanyListingViewModel @Inject constructor(
    private val repo: StockDomainRepoImpl
) : ViewModel() {

    var state by mutableStateOf(CompanyListingsStates())

    private var searchJob: Job? = null

    init {
        getCompanyList()
    }

    fun onEvent(events: CompanyListingsEvents) {
        when (events) {
            is CompanyListingsEvents.OnSearchQueryChange -> {
                state = state.copy(
                    searchQuery = events.query
                )
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyList()
                }
            }
            CompanyListingsEvents.Refresh -> {
                getCompanyList(fetchFromRemote = true)
            }
        }
    }


    private fun getCompanyList(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            repo.getCompanyList(fetchFromRemote, query)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            TODO()
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                state = state.copy(
                                    companies = listings
                                )
                            }
                        }
                    }
                }
        }
    }

}