package edu.pwr.s266867.bmicalculator

data class BmiUiState(
    val weight: Double? = null,
    val height: Double? = null,

    val units: Units = Units.METRIC,
)
