package com.kings.stockapp.presentation.company_listing.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier,
    query: String,
    onValueChanged: (query: String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(10.dp))
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = query,
            onValueChange = onValueChanged,
            placeholder = {
                Text(text = "Search", color = MaterialTheme.colorScheme.onPrimary)
            },
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary),
            shape = RoundedCornerShape(10.dp)
        )
    }
}

@Preview
@Composable
fun SearchBarPreview(){
    SearchBar(modifier = Modifier, query = "Search", onValueChanged = {})
}