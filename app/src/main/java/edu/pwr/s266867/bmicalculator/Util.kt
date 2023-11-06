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
}