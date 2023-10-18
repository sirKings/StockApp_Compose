package com.kings.stockapp.di

import android.app.Application
import androidx.room.Room
import com.kings.stockapp.data.local.CompanyListingDatabase
import com.kings.stockapp.data.remote.StockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(): StockApi{
        return Retrofit
            .Builder()
            .baseUrl(StockApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideStockDb(application: Application): CompanyListingDatabase{
        return Room.databaseBuilder(
            application,
            CompanyListingDatabase::class.java,
            "company_listing.db")
            .build()
    }
}