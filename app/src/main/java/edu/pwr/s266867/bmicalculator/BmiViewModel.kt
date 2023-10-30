package edu.pwr.s266867.bmicalculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BmiViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BmiUiState())
    val uiState: StateFlow<BmiUiState> = _uiState.asStateFlow()

    fun updateWeight(weight: Double) {
        _uiState.update { currentState ->
            currentState.copy(
                weight = weight,
            )
        }
    }

    fun updateHeight(height: Double) {
        _uiState.update { currentState ->
            currentState.copy(
                height = height,
            )
        }
    }

    fun switchUnits() {
        _uiState.update { currentState ->
            currentState.copy(
                units = if (currentState.units == Units.METRIC) Units.IMPERIAL else Units.METRIC
            )
        }
    }

    val units: Units get() = _uiState.value.units
    val bmi: Double? get() =
        if (_uiState.value.weight == null || _uiState.value.height == null)
            null
        else
            BmiCalculator.calculateBmi(_uiState.value.weight!!, _uiState.value.height!!)
}