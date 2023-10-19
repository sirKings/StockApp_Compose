package com.kings.stockapp.presentation.company_listing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kings.stockapp.domain.repository.CompanyListingRepository
import com.kings.stockapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CompanyListingViewModel @Inject constructor(
    private val repository: CompanyListingRepository
): ViewModel() {

    var state by mutableStateOf(CompanyListViewState())

    private var searchJob: Job? = null

    init {
        getCompanyListings()
    }

    fun onEvent(events: CompanyListViewEvents){
        when(events){
            CompanyListViewEvents.Refresh -> {
                getCompanyListings(fromRemote = true)
            }
            is CompanyListViewEvents.OnSearchQuery -> {
                state = state.copy(searchQuery = events.string)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListings()
                }
            }
        }
    }

    private fun getCompanyListings(
        query: String = state.searchQuery.lowercase(),
        fromRemote: Boolean = false
    ){
        viewModelScope.launch {
            repository.getCompanyListings(
                fromRemote = fromRemote,
                queryString = query
            ).collect{ result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { listings ->
                            state = state.copy(companies = listings)
                        }
                    }
                    is Resource.Error -> {
                        result.error?.let {
                            state = state.copy(errorMessage = it)
                        }
                    }
                    is Resource.Loading -> {
                        state = state.copy(loading = true)
                    }
                }
            }
        }
    }
}