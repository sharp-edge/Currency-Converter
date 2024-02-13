package com.sharpedge.currencyconverter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharpedge.currencyconverter.ui.model.CurrencyRecordUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.sharpedge.currencyconverter.utils.Result
import com.sharpedge.currencyconverter.usecase.database.IGetHistoricalDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoricalDataViewModel @Inject constructor(
    private val getHistoricalDataUseCase: IGetHistoricalDataUseCase
) : ViewModel() {
    private val _historicalData = MutableStateFlow<Result<List<CurrencyRecordUIModel>>>(Result.Success(emptyList()))
    val historicalData: StateFlow<Result<List<CurrencyRecordUIModel>>> = _historicalData

    fun loadHistoricalData(daysAgo: Int) {
        val since = System.currentTimeMillis() - daysAgo * 24 * 60 * 60 * 1000 // Last 3 days
        viewModelScope.launch {
            val result = getHistoricalDataUseCase.execute(since)
            _historicalData.value = result
        }
    }
}
