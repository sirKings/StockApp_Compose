package com.kings.stockapp.di

import com.kings.stockapp.data.csv.CSVParser
import com.kings.stockapp.data.csv.CompanyListingParser
import com.kings.stockapp.data.repository.CompanyListingRepositoryImpl
import com.kings.stockapp.domain.model.CompanyListing
import com.kings.stockapp.domain.repository.CompanyListingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCSVParser(
        parser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindRepository(
        repositoryImpl: CompanyListingRepositoryImpl
    ): CompanyListingRepository
}