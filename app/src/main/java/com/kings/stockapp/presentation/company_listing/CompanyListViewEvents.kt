package com.kings.stockapp.presentation.company_listing

sealed class CompanyListViewEvents{
    object Refresh: CompanyListViewEvents()
    data class OnSearchQuery(val string: String): CompanyListViewEvents()
}
