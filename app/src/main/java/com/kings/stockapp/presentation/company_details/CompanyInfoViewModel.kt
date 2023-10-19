package com.kings.stockapp.presentation.company_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kings.stockapp.domain.repository.CompanyListingRepository
import com.kings.stockapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CompanyListingRepository
) : ViewModel() {
    val symbol = savedStateHandle.get<String>("symbol") ?: ""

    var state by mutableStateOf(CompanyInfoViewState())

    init {
        fetchCompanyInfo()
        fetchIntraDayData()
    }

    private fun fetchCompanyInfo() {
        viewModelScope.launch {
            repository
                .getCompanyInfo(symbol)
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            state = state.copy(
                                isLoading = false,
                                companyInfo = it.data,
                                error = null
                            )
                        }

                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                error = it.error
                            )
                        }

                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = true
                            )
                        }
                    }
                }
        }
    }

    private fun fetchIntraDayData() {
        viewModelScope.launch {
            repository
                .getIntraDayInfo(symbol)
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            state = state.copy(
                                isLoading = false,
                                intraDayData = it.data ?: emptyList(),
                                error = null
                            )
                        }

                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                error = it.error
                            )
                        }

                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = true
                            )
                        }
                    }
                }
        }
    }

}