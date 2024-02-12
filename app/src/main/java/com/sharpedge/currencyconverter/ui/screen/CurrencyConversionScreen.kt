package com.sharpedge.currencyconverter.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sharpedge.currencyconverter.ui.state.ErrorType
import com.sharpedge.currencyconverter.ui.component.CurrencyDropdownMenusAndConversionFields
import com.sharpedge.currencyconverter.utils.toUserFriendlyMessage
import kotlinx.coroutines.launch
import com.sharpedge.currencyconverter.viewmodel.CurrencyConversionViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConversionScreen(viewModel: CurrencyConversionViewModel = hiltViewModel(), onDetailsClicked: () -> Unit ) {
    val viewState by viewModel.viewState.collectAsState()
    val snackbarHostState = SnackbarHostState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {

            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()) {


                CurrencyDropdownMenusAndConversionFields(
                    fromCurrencySymbols = viewState.currencySymbols,
                    toCurrencySymbols = viewState.currencySymbols,
                    fromCurrency = viewState.fromCurrency,
                    toCurrency = viewState.toCurrency,
                    conversionAmount = viewState.conversionAmount.toString(), // Use the state's conversionAmount
                    conversionResult = viewState.conversionResult,
                    onFromCurrencyChanged = { viewModel.setFromCurrency(it) },
                    onToCurrencyChanged = { viewModel.setToCurrency(it) },
                    onAmountChanged = { amount -> viewModel.setConversionAmount(amount) }, // Update amount in state
                    onSwapClicked = { viewModel.swapCurrencies() },
                    onDetailsClicked = onDetailsClicked

                )


            }


            if (viewState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }


            viewState.error?.let { error ->
                val errorMessage = when (error) {
                    is ErrorType.Custom -> error.message
                    else -> error.toUserFriendlyMessage()
                }
                LaunchedEffect(key1 = error) {
                    coroutineScope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = errorMessage,
                            duration = SnackbarDuration.Long
                        )
                        if (result == SnackbarResult.ActionPerformed || result == SnackbarResult.Dismissed) {
                            viewModel.clearError()
                        }
                    }
                }
            }
        }
    }
}
