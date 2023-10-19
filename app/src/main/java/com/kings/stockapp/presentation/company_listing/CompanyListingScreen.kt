package com.kings.stockapp.presentation.company_listing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.kings.stockapp.presentation.company_listing.component.Listing
import com.kings.stockapp.presentation.company_listing.component.SearchBar

@Composable
fun CompanyListingScreen(
    viewModel: CompanyListingViewModel = hiltViewModel()
) {
    val state = viewModel.state

    val refreshState = rememberSwipeRefreshState(
        isRefreshing = state.isRefreshing
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(horizontal = 16.dp, vertical = 20.dp),
    ) {
        SearchBar(
            modifier = Modifier,
            query = state.searchQuery,
            onValueChanged = {
                viewModel.onEvent(
                    CompanyListViewEvents.OnSearchQuery(it)
                )
            }
        )

        SwipeRefresh(state = refreshState, onRefresh = {
            viewModel.onEvent(CompanyListViewEvents.Refresh)
        }) {
            LazyColumn(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                items(state.companies.size) { i ->
                    val item = state.companies[i]
                    Listing(modifier = Modifier, item = item)
                    if (i < state.companies.size) {
                        Divider()
                    }
                }
            }
        }
    }

}