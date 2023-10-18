package com.kings.stockapp.presentation.company_listing

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Preview
@Composable
fun CompanyListingScreen(
    viewModel: CompanyListingViewModel = hiltViewModel(),
){
    val state = viewModel.state

    val refreshState = rememberSwipeRefreshState(
        isRefreshing = state.isRefreshing
    )


    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.DarkGray)
        .padding(horizontal = 16.dp, vertical = 20.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp))
        ) {


        }
    }

}