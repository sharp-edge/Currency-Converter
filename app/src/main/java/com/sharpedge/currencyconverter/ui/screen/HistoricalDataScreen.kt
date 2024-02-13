package com.sharpedge.currencyconverter.ui.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.sharpedge.currencyconverter.utils.Result
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sharpedge.currencyconverter.ui.model.CurrencyRecordUIModel
import com.sharpedge.currencyconverter.viewmodel.HistoricalDataViewModel

@Composable
fun HistoricalDataScreen() {
    val viewModel: HistoricalDataViewModel = hiltViewModel()
    val historicalData by viewModel.historicalData.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadHistoricalData(3) // Load last 3 days data
    }

    when (historicalData) {
        is Result.Success -> {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items((historicalData as Result.Success<List<CurrencyRecordUIModel>>).data) { record ->
                    Text(
                        text = "From: ${record.fromCurrency} to ${record.toCurrency}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Time: ${record.date}", style = MaterialTheme.typography.bodyMedium)
                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }

        is Result.Failure -> {
            Text("Failed to load data")
        }
        //else -> Unit // Handle other states if necessary

    }
}
