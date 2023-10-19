package com.kings.stockapp.data.mapper

import com.kings.stockapp.data.remote.dto.IntraDayInfoDto
import com.kings.stockapp.domain.model.IntraDayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun IntraDayInfoDto.toIntradayInfo(): IntraDayInfo{
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localTime = LocalDateTime.parse(timestamp,formatter)
    return IntraDayInfo(localTime,close)
}