package com.tarekrefaei.stockmarketapp.features.market_history.domain.model

import java.time.LocalDateTime

data class IntraDayInfo(
    val date: LocalDateTime,
    val close: Double
)