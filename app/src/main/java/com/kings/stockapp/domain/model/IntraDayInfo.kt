package com.kings.stockapp.domain.model

import java.time.LocalDateTime

data class IntraDayInfo(
    val timestamp: LocalDateTime,
    val close: Double
)
