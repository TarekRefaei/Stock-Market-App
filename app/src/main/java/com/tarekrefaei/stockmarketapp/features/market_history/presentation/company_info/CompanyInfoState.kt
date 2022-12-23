package com.tarekrefaei.stockmarketapp.features.market_history.presentation.company_info

import com.tarekrefaei.stockmarketapp.features.market_history.domain.model.CompanyInfo
import com.tarekrefaei.stockmarketapp.features.market_history.domain.model.IntraDayInfo

data class CompanyInfoState(
    val stockInfo: List<IntraDayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
