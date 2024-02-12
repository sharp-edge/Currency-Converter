package com.sharpedge.currencyconverter.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyDropdownMenusAndConversionFields(
    fromCurrencySymbols: List<String>,
    toCurrencySymbols: List<String>,
    fromCurrency: String?,
    toCurrency: String?,
    conversionAmount: String,
    conversionResult: String?,
    onFromCurrencyChanged: (String) -> Unit,
    onToCurrencyChanged: (String) -> Unit,
    onAmountChanged: (String) -> Unit,
    onSwapClicked: () -> Unit,
    onDetailsClicked: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        CurrencyDropdownMenu(
            label = "From",
            options = fromCurrencySymbols,
            selectedOption = fromCurrency ?: "",
            onSelectionChange = onFromCurrencyChanged,
            modifier = Modifier.weight(1f)
        )

        Spacer(Modifier.width(5.dp))
        Button(
            onClick = { onSwapClicked() },
            enabled = fromCurrency != null && toCurrency != null,
            modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
        ) {

            Text("Swap")

        }
        Spacer(Modifier.width(5.dp))

        CurrencyDropdownMenu(
            modifier = Modifier.weight(1f),
            label = "To",
            options = toCurrencySymbols,
            selectedOption = toCurrency ?: "",
            onSelectionChange = onToCurrencyChanged

        )
    }
    Spacer(Modifier.height(16.dp))


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = conversionAmount,
            onValueChange = onAmountChanged,
            label = { Text("Amount") },
            modifier = Modifier.weight(1f),
            readOnly = fromCurrency.isNullOrEmpty() || toCurrency.isNullOrEmpty() // Read-only if currencies not selected
        )

        Spacer(Modifier.width(16.dp))

        OutlinedTextField(
            value = conversionResult ?: "",
            onValueChange = { /* Result field, no action needed */ },
            label = { Text("Converted") },
            modifier = Modifier.weight(1f),
            readOnly = true // Always read-only
        )
    }

    Spacer(Modifier.height(16.dp))


    Button(
        onClick = { onDetailsClicked() },
        enabled = fromCurrency != null && toCurrency != null,
//        enabled = true,
        modifier = Modifier.fillMaxWidth().padding(30.dp)
    ) {

        Text("Details")

    }
}
