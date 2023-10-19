package com.kings.stockapp.presentation.company_details

import com.kings.stockapp.domain.model.CompanyInfo
import com.kings.stockapp.domain.model.IntraDayInfo

data class CompanyInfoViewState(
    val isLoading: Boolean = false,
    val intraDayData: List<IntraDayInfo> = emptyList(),
    val companyInfo: CompanyInfo? = null,
    val error: String? = null
)
