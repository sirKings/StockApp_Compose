package com.kings.stockapp.data.repository

import com.kings.stockapp.data.local.CompanyListingDatabase
import com.kings.stockapp.data.mapper.toCompanyList
import com.kings.stockapp.data.remote.StockApi
import com.kings.stockapp.domain.model.CompanyListing
import com.kings.stockapp.domain.repository.CompanyListingRepository
import com.kings.stockapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CompanyListingRepositoryImpl @Inject constructor(
    val api: StockApi,
    val db: CompanyListingDatabase
): CompanyListingRepository {

    override fun getCompanyListings(
        fromRemote: Boolean,
        queryString: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = db.dao.searchListings(queryString)
            emit(
                Resource.Success(
                    data = localListings.map {
                        it.toCompanyList()
                    }
                )
            )
            val isCacheEmpty = localListings.isEmpty() && queryString.isBlank()
            val justLoadOnlyCache = !isCacheEmpty && !fromRemote
            if(justLoadOnlyCache){
                emit(Resource.Loading(false))
                return@flow
            }else{
                val remoteListings = try {
                    api.getListings()
                }catch (e: Exception){
                    e.printStackTrace()
                    emit(Resource.Error(error = "Error ${e.localizedMessage ?: "Could not load data"}"))
                }

            }
        }
    }
}