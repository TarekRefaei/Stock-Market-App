package com.tarekrefaei.stockmarketapp.features.market_history.data.csv

import java.io.InputStream

interface CSVParser<T> {
    suspend fun parse(stream: InputStream) : List<T>
}