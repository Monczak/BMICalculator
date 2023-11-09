package edu.pwr.s266867.bmicalculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.pwr.s266867.bmicalculator.Util.roundToDecimal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BmiViewModel : ViewModel() {
    val weight: MutableLiveData<Double> by lazy { MutableLiveData<Double>() }
    val height: MutableLiveData<Double> by lazy { MutableLiveData<Double>() }
    val units: MutableLiveData<Units> by lazy { MutableLiveData<Units>() }
    val bmi: MutableLiveData<Double?> by lazy { MutableLiveData<Double?>() }

    private fun tryConvert(data: MutableLiveData<Double>, measurement: Measurement, fromUnits: Units?, toUnits: Units?) {
        if (data.value != null && fromUnits != null && toUnits != null)
            data.value = (data.value!! * UnitConverter.getConversion(measurement, fromUnits, toUnits)).roundToDecimal(1)
    }

    fun switchUnits() {
        val oldUnits = units.value

        units.value = when (units.value) {
            Units.METRIC -> Units.IMPERIAL
            Units.IMPERIAL -> Units.METRIC
            else -> Units.METRIC
        }

        tryConvert(weight, Measurement.WEIGHT, oldUnits, units.value)
        tryConvert(height, Measurement.HEIGHT, oldUnits, units.value)
    }

    fun calculateBmi() {
        if (weight.value != null && height.value != null && units.value != null)
            bmi.value = BmiCalculator.calculateBmi(
                weight.value!! * UnitConverter.getConversion(Measurement.WEIGHT, units.value!!, Units.METRIC),
                height.value!! * UnitConverter.getConversion(Measurement.HEIGHT, units.value!!, Units.METRIC), )
    }
}