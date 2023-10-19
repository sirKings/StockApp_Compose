package com.kings.stockapp.presentation.company_listing

import androidx.compose.ui.tooling.preview.PreviewParameter
import com.kings.stockapp.domain.model.CompanyListing


data class CompanyListViewState(
    val companies: List<CompanyListing> = emptyList(),
    val loading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val errorMessage: String? = null
)