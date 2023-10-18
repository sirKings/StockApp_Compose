package com.kings.stockapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("listing")
data class CompanyListingEntity(
    val name: String,
    val symbol:String,
    val exchange: String,
    @PrimaryKey val id: Int? = null
)
