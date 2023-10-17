package com.kings.stockapp.data.local

import androidx.room.PrimaryKey

data class CompanyListingEntity(
    val name: String,
    val symbol:String,
    val exchange: String,
    @PrimaryKey val id: Int? = null
)
