package com.kings.stockapp.data.local

import androidx.room.Database


@Database(
    entities = [CompanyListingEntity::class],
    version = 1
)
abstract class CompanyListingDatabase {
    abstract val dao: CompanyListingDao
}