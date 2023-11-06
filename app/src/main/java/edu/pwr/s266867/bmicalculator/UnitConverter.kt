package edu.pwr.s266867.bmicalculator

object UnitConverter {
    private const val KILOGRAMS_TO_POUNDS = 2.20462
    private const val INCHES_TO_CENTIMETERS = 2.54

    private data class Conversion(val measurement: Measurement, val fromUnits: Units, val toUnits: Units)

    // I know this is overkill
    fun getConversion(measurement: Measurement, fromUnits: Units, toUnits: Units) : Double {
        if (fromUnits == toUnits)
            return 1.0
        return when (Conversion(measurement, fromUnits, toUnits)) {
            Conversion(Measurement.WEIGHT, Units.METRIC, Units.IMPERIAL) -> KILOGRAMS_TO_POUNDS
            Conversion(Measurement.WEIGHT, Units.IMPERIAL, Units.METRIC) -> 1 / KILOGRAMS_TO_POUNDS
            Conversion(Measurement.HEIGHT, Units.METRIC, Units.IMPERIAL) -> 1 / INCHES_TO_CENTIMETERS
            Conversion(Measurement.HEIGHT, Units.IMPERIAL, Units.METRIC) -> INCHES_TO_CENTIMETERS
            else -> throw IllegalArgumentException("Unknown conversion")
        }
    }

}