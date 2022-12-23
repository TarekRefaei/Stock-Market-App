package com.tarekrefaei.stockmarketapp.features.market_history.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarekrefaei.stockmarketapp.features.market_history.domain.repo.StockDomainRepoImpl
import com.tarekrefaei.stockmarketapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repo: StockDomainRepoImpl
) : ViewModel() {

    var state by mutableStateOf(CompanyInfoState())

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            state = state.copy(isLoading = true)
            val companyInfoResult = async { repo.getCompanyInfo(symbol) }
            val intraDayInfoResult = async { repo.getIntraDayInfo(symbol) }
            when (val result = companyInfoResult.await()) {
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        company = null,
                        error = result.message
                    )
                }
                is Resource.Loading -> {
                    state = state.copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        error = null,
                        company = result.data
                    )
                }
            }
            when (val result = intraDayInfoResult.await()){
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        stockInfo = emptyList(),
                        error = result.message
                    )
                }
                is Resource.Loading -> {
                    state = state.copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        error = null,
                        stockInfo = result.data ?: emptyList(),
                    )
                }
            }
        }
    }

}