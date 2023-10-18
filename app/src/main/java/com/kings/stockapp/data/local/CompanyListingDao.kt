package com.kings.stockapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface CompanyListingDao {
    @Insert
    suspend fun insert(listings: List<CompanyListingEntity>)

    @Query("DELETE FROM companylistingentity")
    suspend fun clearListings()

    @Query("""
        SELECT * FROM companylistingentity 
                WHERE LOWER(name) LIKE '%' || 
                LOWER(:queryString) || '%' OR 
                UPPER(:queryString) == symbol
        """)
    suspend fun searchListings(queryString: String): List<CompanyListingEntity>


}