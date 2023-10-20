package edu.pwr.s266867.bmicalculator

import android.graphics.Color

object BmiCalculator {
    fun calculateBmi(weightKg: Double, heightCm: Double): Double
        = weightKg / ((heightCm / 100) * (heightCm / 100))

    fun getBmiCategory(bmi: Double): BmiCategory = when {
        bmi < 18.5 -> BmiCategory.UNDERWEIGHT
        bmi in 18.5..< 25.0 -> BmiCategory.NORMAL
        bmi in 25.0 ..< 30.0 -> BmiCategory.OVERWEIGHT
        else -> BmiCategory.OBESE
    }

    fun getBmiColor(bmiCategory: BmiCategory) : Int = when (bmiCategory) {
        BmiCategory.UNDERWEIGHT -> R.color.bmiUnderweight
        BmiCategory.NORMAL -> R.color.bmiNormal
        BmiCategory.OVERWEIGHT -> R.color.bmiOverweight
        BmiCategory.OBESE -> R.color.bmiObese
    }

    fun getBmiColor(bmi: Double) : Int = getBmiColor(getBmiCategory(bmi))
}