package com.kings.stockapp.domain.repository

import com.kings.stockapp.domain.model.CompanyListing
import com.kings.stockapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface CompanyListingRepository {
    fun getCompanyListings(
        fromRemote: Boolean,
        queryString: String
    ): Flow<Resource<List<CompanyListing>>>
}