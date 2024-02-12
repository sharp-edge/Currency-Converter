package com.sharpedge.currencyconverter.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyDropdownMenu(
    label: String,
    options: List<String>,
    selectedOption: String?,
    onSelectionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val showDialog = remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        OutlinedTextField(
            readOnly = true,
            value = selectedOption ?: "",
            onValueChange = { },
            label = { Text(text = label) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown"
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
        // Invisible clickable overlay
        Spacer(modifier = Modifier
            .matchParentSize() // Make the spacer match the size of the OutlinedTextField
            .clickable {
                showDialog.value = true // Show dialog on click
            }
        )
    }

    // Show the dialog when OutlinedTextField is clicked

    if (showDialog.value) {
        CurrencySelectionDialog(
            options = options,
            onOptionSelected = { selection ->
                onSelectionChange(selection)
                showDialog.value = false
            },
            showDialog = showDialog
        )
    }
}
