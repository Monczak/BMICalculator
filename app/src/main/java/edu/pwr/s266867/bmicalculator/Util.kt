package edu.pwr.s266867.bmicalculator

import kotlin.math.roundToInt

object Util {
    fun Double.roundToDecimal(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return (this * multiplier).roundToInt() / multiplier
    }

    fun validateDouble(text: String, validator: (Double) -> Boolean): Pair<Boolean, Double?> {
        val textValue: Double = text.toDoubleOrNull() ?: return Pair(false, null)

        val valid: Boolean = validator(textValue)
        return Pair(valid, if (valid) textValue else null)
    }

    private data class MeasurementUnits(val measurement: Measurement, val units: Units)
    fun getUnitText(measurement: Measurement, units: Units) = when (MeasurementUnits(measurement, units)) {
        MeasurementUnits(Measurement.WEIGHT, Units.METRIC) -> R.string.weight_metric_unit
        MeasurementUnits(Measurement.WEIGHT, Units.IMPERIAL) -> R.string.weight_imperial_unit
        MeasurementUnits(Measurement.HEIGHT, Units.METRIC) -> R.string.height_metric_unit
        MeasurementUnits(Measurement.HEIGHT, Units.IMPERIAL) -> R.string.height_imperial_unit
        else -> throw IllegalArgumentException("Unknown measurement-unit pair")
    }
}