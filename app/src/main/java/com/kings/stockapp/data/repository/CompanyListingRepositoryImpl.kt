package com.kings.stockapp.data.repository

import com.kings.stockapp.data.csv.CSVParser
import com.kings.stockapp.data.local.CompanyListingDatabase
import com.kings.stockapp.data.mapper.toCompanyInfo
import com.kings.stockapp.data.mapper.toCompanyList
import com.kings.stockapp.data.mapper.toCompanyListEntity
import com.kings.stockapp.data.remote.StockApi
import com.kings.stockapp.domain.model.CompanyInfo
import com.kings.stockapp.domain.model.CompanyListing
import com.kings.stockapp.domain.model.IntraDayInfo
import com.kings.stockapp.domain.repository.CompanyListingRepository
import com.kings.stockapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CompanyListingRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: CompanyListingDatabase,
    private val csvParser: CSVParser<CompanyListing>,
    private val intraDayParser: CSVParser<IntraDayInfo>
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
                    csvParser.parse(api.getListings().byteStream())
                }catch (e: Exception){
                    e.printStackTrace()
                    emit(Resource.Error(error = "Error ${e.localizedMessage ?: "Could not load data"}"))
                    null
                }

                remoteListings?.let {
                    emit(Resource.Success(data = it))
                    db.dao.clearListings()
                    db.dao.insert(
                        it.map {
                                companyListing -> companyListing.toCompanyListEntity()
                        })
                }
            }
        }
    }

    override fun getCompanyInfo(symbol: String): Flow<Resource<CompanyInfo>> {
        return flow {
            emit(Resource.Loading(true))
            val info = try {
                api.getCompanyInfo(symbol)
            }catch (e: Exception){
                e.printStackTrace()
                emit(Resource.Error(error = "Error ${e.localizedMessage ?: "Could not load data"}"))
                null
            }
            info?.let {
                emit(Resource.Success(data = it.toCompanyInfo()))
            }
        }
    }

    override fun getIntraDayInfo(symbol: String): Flow<Resource<List<IntraDayInfo>>> {
        return flow {
            emit(Resource.Loading(true))
            val intraDayInfo = try {
                val dto = intraDayParser.parse(api.getIntradayInfo(symbol).byteStream())
                dto
            }catch (e: Exception){
                e.printStackTrace()
                emit(Resource.Error(error = "Error ${e.localizedMessage ?: "Could not load data"}"))
                null
            }
            intraDayInfo?.let {
                emit(Resource.Success(data = it))
            }
        }
    }
}