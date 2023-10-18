package com.kings.stockapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CompanyListingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listings: List<CompanyListingEntity>)

    @Query("DELETE FROM listing")
    suspend fun clearListings()

    @Query("""
        SELECT * FROM listing 
                WHERE LOWER(name) LIKE '%' || 
                LOWER(:queryString) || '%' OR 
                UPPER(:queryString) == symbol
        """)
    suspend fun searchListings(queryString: String): List<CompanyListingEntity>


}