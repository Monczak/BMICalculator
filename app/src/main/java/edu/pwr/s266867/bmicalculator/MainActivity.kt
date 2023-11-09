// Testing devices:
// Virtual: Pixel 3a (API 34)
// Physical: OnePlus 7T Pro (Android 12)

package edu.pwr.s266867.bmicalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.pwr.s266867.bmicalculator.Util.roundToDecimal

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BmiViewModel

    private val defaultUnits: Units = Units.METRIC

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        viewModel = ViewModelProvider(this)[BmiViewModel::class.java]

        setupUI()
    }

    private fun setupUI() {
        val weightInputField = findViewById<EditText>(R.id.weightInput)
        val heightInputField = findViewById<EditText>(R.id.heightInput)

        val weightUnitText = findViewById<TextView>(R.id.weightInputUnitText)
        val heightUnitText = findViewById<TextView>(R.id.heightInputUnitText)

        val bmiText = findViewById<TextView>(R.id.bmiValueText)

        val weightObserver =
            Observer<Double> { newWeight -> weightInputField.setText(newWeight.toString()) }
        val heightObserver =
            Observer<Double> { newHeight -> heightInputField.setText(newHeight.toString()) }

        val unitsObserver = Observer<Units> { newUnits ->
            weightUnitText.setText(
                when (newUnits) {
                    Units.METRIC -> R.string.weight_metric_unit
                    Units.IMPERIAL -> R.string.weight_imperial_unit
                    else -> R.string.weight_metric_unit
                }
            )
            heightUnitText.setText(
                when (newUnits) {
                    Units.METRIC -> R.string.height_metric_unit
                    Units.IMPERIAL -> R.string.height_imperial_unit
                    else -> R.string.height_metric_unit
                }
            )
        }

        val bmiObserver = Observer<Double?> { newBmi ->
            if (newBmi == null)
                bmiText.text = ""
            else {
                bmiText.text = "%.1f".format(newBmi.roundToDecimal(1))
                bmiText.setTextColor(getColor(BmiCalculator.getBmiColor(newBmi)))
            }
        }

        viewModel.weight.observe(this, weightObserver)
        viewModel.height.observe(this, heightObserver)
        viewModel.units.observe(this, unitsObserver)
        viewModel.bmi.observe(this, bmiObserver)

        viewModel.units.value = defaultUnits
        viewModel.bmi.value = null

        findViewById<Button>(R.id.calculateButton).setOnClickListener {
            val (weightInputValid, weightInput) = Util.validateDouble(weightInputField.text.toString()) { input -> input > 0 }
            val (heightInputValid, heightInput) = Util.validateDouble(heightInputField.text.toString()) { input -> input > 0 }

            if (weightInputValid)
                viewModel.weight.value = weightInput
            else
                showToast(getText(R.string.invalid_weight))

            if (heightInputValid)
                viewModel.height.value = heightInput
            else
                showToast(getText(R.string.invalid_height))

            if (weightInputValid && heightInputValid)
                viewModel.calculateBmi()
        }

        findViewById<TextView>(R.id.bmiValueText).setOnClickListener {
            if (viewModel.bmi.value != null) {
                val intent = Intent(this, BmiDetailsActivity::class.java)
                intent.putExtra("bmi", viewModel.bmi.value)
                startActivity(intent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onMenuOpened(featureId: Int, menu: Menu): Boolean {
        val switchUnitsMenuItem = menu.findItem(R.id.switchUnitsMenuItem)

        switchUnitsMenuItem?.setTitle(when (viewModel.units.value) {
            Units.METRIC -> R.string.switch_units_imperial_menuitem
            Units.IMPERIAL -> R.string.switch_units_metric_menuitem
            else -> throw IllegalArgumentException("Cannot handle units of type ${viewModel.units.value}")
        })

        return super.onMenuOpened(featureId, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.switchUnitsMenuItem -> viewModel.switchUnits()
        }
        return true
    }

    private fun showToast(text: CharSequence) {
        val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
        toast.show()
    }
}