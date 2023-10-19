package com.kings.stockapp.data.csv

import com.kings.stockapp.data.mapper.toIntradayInfo
import com.kings.stockapp.data.remote.dto.IntraDayInfoDto
import com.kings.stockapp.domain.model.CompanyListing
import com.kings.stockapp.domain.model.IntraDayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class IntraDayInfoParser @Inject constructor() : CSVParser<IntraDayInfo> {
    override suspend fun parse(stream: InputStream): List<IntraDayInfo> {
        val reader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            reader.readAll()
                .drop(1)
                .mapNotNull {
                    val timestamp = it.getOrNull(0) ?: return@mapNotNull null
                    val close = it.getOrNull(4) ?: return@mapNotNull null
                    val info = IntraDayInfoDto(timestamp, close.toDouble())
                    info.toIntradayInfo()
                }.filter {
                    it.timestamp.dayOfMonth == LocalDateTime.now().minusDays(1).dayOfMonth
                }.sortedBy {
                    it.timestamp.hour
                }.also {
                    reader.close()
                }
        }
    }
}