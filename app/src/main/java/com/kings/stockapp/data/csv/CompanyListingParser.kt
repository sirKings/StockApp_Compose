package com.kings.stockapp.data.csv

import com.kings.stockapp.domain.model.CompanyListing
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CompanyListingParser @Inject constructor() : CSVParser<CompanyListing> {
    override suspend fun parse(stream: InputStream): List<CompanyListing> {
        val reader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            reader.readAll().drop(1).mapNotNull {
                val name = it.getOrNull(0)
                val symbol = it.getOrNull(1)
                val exchange = it.getOrNull(2)
                CompanyListing(
                    name ?: return@mapNotNull null,
                    symbol ?: return@mapNotNull null,
                    exchange ?: return@mapNotNull null
                )
            }.also {
                reader.close()
            }
        }
    }
}