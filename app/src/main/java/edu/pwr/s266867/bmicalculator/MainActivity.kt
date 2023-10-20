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
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import edu.pwr.s266867.bmicalculator.Util.roundToDecimal

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val calculateButton = findViewById<Button>(R.id.calculateButton)
        calculateButton.setOnClickListener {
            val weight = findViewById<EditText>(R.id.weightInput).text.toString().toDouble()
            val height = findViewById<EditText>(R.id.heightInput).text.toString().toDouble()
            val bmi = BmiCalculator.calculateBmi(weight, height)

            updateBmiText(bmi)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onMenuOpened(featureId: Int, menu: Menu): Boolean {
        val switchUnitsMenuItem = menu.findItem(R.id.switchUnitsMenuItem)

        switchUnitsMenuItem?.setTitle(when (Options.units) {
            Units.METRIC -> R.string.switch_units_imperial_menuitem
            Units.IMPERIAL -> R.string.switch_units_metric_menuitem
            else -> throw IllegalArgumentException("Cannot handle units of type ${Options.units}")
        })

        return super.onMenuOpened(featureId, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.switchUnitsMenuItem -> {
                Options.units = if (Options.units == Units.METRIC) Units.IMPERIAL else Units.METRIC
            }
        }
        return true
    }

    private fun updateBmiText(bmi: Double) {
        val textView = findViewById<TextView>(R.id.bmiValueText)
        textView.text = "%.1f".format(bmi.roundToDecimal(1))
        textView.setTextColor(getColor(BmiCalculator.getBmiColor(bmi)))
    }
}