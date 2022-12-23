package com.tarekrefaei.stockmarketapp.features.market_history.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
//Every row in table
data class CompanyListingEntity(
    @PrimaryKey val id: Int? = null,
    val name : String,
    val symbol : String,
    val exchange : String,
)
