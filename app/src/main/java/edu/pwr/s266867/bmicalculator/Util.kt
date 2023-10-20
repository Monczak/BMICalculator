package edu.pwr.s266867.bmicalculator

import kotlin.math.roundToInt

object Util {
    fun Double.roundToDecimal(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return (this * multiplier).roundToInt() / multiplier
    }
}