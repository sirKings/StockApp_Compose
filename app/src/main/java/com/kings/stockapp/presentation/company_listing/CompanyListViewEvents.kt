package com.kings.stockapp.presentation.company_listing

sealed class CompanyListViewEvents{
    object Refresh: CompanyListViewEvents()
    data class onSearchQuery(val string: String): CompanyListViewEvents()
}
