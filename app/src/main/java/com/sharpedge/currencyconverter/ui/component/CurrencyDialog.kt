package com.sharpedge.currencyconverter.ui.component


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun CurrencySelectionDialog(
    options: List<String>, onOptionSelected: (String) -> Unit, showDialog: MutableState<Boolean>
) {
    CustomCurrencySelectionDialog(
        title = "Select Currency",
        options = options,
        onOptionSelected = onOptionSelected,
        showDialog = showDialog
    )
}


@Composable
private fun CustomCurrencySelectionDialog(
    showDialog: MutableState<Boolean>,
    title: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    if (showDialog.value) {
        Dialog(onDismissRequest = { showDialog.value = false }) {
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier.padding(all = 16.dp)
                ) {
                    Text(
                        text = title, style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))


                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 400.dp) // Adjust this value as needed
                    ) {
                        items(options) { currency ->
                            TextButton(
                                onClick = {
                                    onOptionSelected(currency)
                                    showDialog.value = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = currency)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))


                    Button(
                        onClick = { showDialog.value = false },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}
