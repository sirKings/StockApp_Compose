package com.kings.stockapp.data.mapper

import com.kings.stockapp.data.local.CompanyListingEntity
import com.kings.stockapp.data.remote.dto.CompanyInfoDto
import com.kings.stockapp.domain.model.CompanyInfo
import com.kings.stockapp.domain.model.CompanyListing

fun CompanyListing.toCompanyListEntity(): CompanyListingEntity {
    return CompanyListingEntity(name, symbol, exchange)
}

fun CompanyListingEntity.toCompanyList(): CompanyListing {
    return CompanyListing(name, symbol, exchange)
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(symbol ?: "", description ?: "", name ?: "", country ?: "", industry ?: "")
}