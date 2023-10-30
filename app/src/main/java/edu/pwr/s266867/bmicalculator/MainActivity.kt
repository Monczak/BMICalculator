// Testing devices:
// Virtual: Pixel 3a (API 34)
// Physical: OnePlus 7T Pro (Android 12)

package edu.pwr.s266867.bmicalculator

import android.annotation.SuppressLint
import android.app.ActionBar.OnMenuVisibilityListener
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import edu.pwr.s266867.bmicalculator.Util.roundToDecimal

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BmiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        viewModel = ViewModelProvider(this)[BmiViewModel::class.java]

        val calculateButton = findViewById<Button>(R.id.calculateButton)
        calculateButton.setOnClickListener {
            val weightStr = findViewById<EditText>(R.id.weightInput).text.toString()
            val heightStr = findViewById<EditText>(R.id.heightInput).text.toString()

            val (weightValid, weight) = validateAndGetInput(weightStr) { w -> w > 0 }
            if (!weightValid) showToast(getText(R.string.invalid_weight))
            else viewModel.updateWeight(weight!!)

            val (heightValid, height) = validateAndGetInput(heightStr) { w -> w > 0 }
            if (!heightValid) showToast(getText(R.string.invalid_height))
            else viewModel.updateHeight(height!!)

            updateBmiText()
        }

        updateBmiText()
        updateUnitTexts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onMenuOpened(featureId: Int, menu: Menu): Boolean {
        val switchUnitsMenuItem = menu.findItem(R.id.switchUnitsMenuItem)

        switchUnitsMenuItem?.setTitle(when (viewModel.units) {
            Units.METRIC -> R.string.switch_units_imperial_menuitem
            Units.IMPERIAL -> R.string.switch_units_metric_menuitem
            else -> throw IllegalArgumentException("Cannot handle units of type ${viewModel.units}")
        })

        return super.onMenuOpened(featureId, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.switchUnitsMenuItem -> viewModel.switchUnits()
        }
        updateUnitTexts()
        return true
    }

    private fun showToast(text: CharSequence) {
        val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
        toast.show()
    }

    private fun validateAndGetInput(text: String, validator: (Double) -> Boolean): Pair<Boolean, Double?> {
        val textValue: Double = text.toDoubleOrNull() ?: return Pair(false, null)

        val valid: Boolean = validator(textValue)
        return Pair(valid, if (valid) textValue else null)
    }

    private fun updateBmiText() {
        val textView = findViewById<TextView>(R.id.bmiValueText)
        val bmi = viewModel.bmi
        if (bmi == null) {
            textView.text = ""
        }
        else {
            textView.text = "%.1f".format(bmi.roundToDecimal(1))
            textView.setTextColor(getColor(BmiCalculator.getBmiColor(bmi)))
        }
    }

    private fun updateUnitTexts() {
        val units = viewModel.units

        val weightUnitText = findViewById<TextView>(R.id.weightInputUnitText)
        weightUnitText.text = when (units) {
            Units.METRIC -> getText(R.string.weight_metric_unit)
            Units.IMPERIAL -> getText(R.string.weight_imperial_unit)
        }

        val heightUnitText = findViewById<TextView>(R.id.heightInputUnitText)
        heightUnitText.text = when (units) {
            Units.METRIC -> getText(R.string.height_metric_unit)
            Units.IMPERIAL -> getText(R.string.height_imperial_unit)
        }
    }
}