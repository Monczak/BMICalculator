package edu.pwr.s266867.bmicalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import edu.pwr.s266867.bmicalculator.Util.roundToDecimal

class BmiDetailsActivity : AppCompatActivity() {

    // TODO: This is ugly, is there a better way to do this?
    private val bmiCategoryTextMap = mapOf(
        BmiCategory.NORMAL to R.string.bmi_category_normal,
        BmiCategory.OVERWEIGHT to R.string.bmi_category_overweight,
        BmiCategory.OBESE to R.string.bmi_category_obese,
        BmiCategory.UNDERWEIGHT to R.string.bmi_category_underweight,
    )

    private val bmiDetailsTextMap = mapOf(
        BmiCategory.NORMAL to R.string.bmi_normal_details,
        BmiCategory.OVERWEIGHT to R.string.bmi_overweight_details,
        BmiCategory.OBESE to R.string.bmi_obese_details,
        BmiCategory.UNDERWEIGHT to R.string.bmi_underweight_details,
    )

    private lateinit var bmiTipsTextMap: Map<BmiCategory, Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_details)

        val toolbar = findViewById<Toolbar>(R.id.toolbar2)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        bmiTipsTextMap = mapOf(
            BmiCategory.NORMAL to resources.getStringArray(R.array.bmi_normal_tips),
            BmiCategory.OVERWEIGHT to resources.getStringArray(R.array.bmi_overweight_tips),
            BmiCategory.OBESE to resources.getStringArray(R.array.bmi_obese_tips),
            BmiCategory.UNDERWEIGHT to resources.getStringArray(R.array.bmi_underweight_tips),
        )

        setupUI()
    }

    private fun setupUI() {
        val bmiValueText = findViewById<TextView>(R.id.bmiValueTextInDetails)
        val bmiCategoryText = findViewById<TextView>(R.id.bmiCategoryText)

        val bmi = intent.getDoubleExtra("bmi", 0.0)
        bmiValueText.text = "%.1f".format(bmi.roundToDecimal(1))

        val category = BmiCalculator.getBmiCategory(bmi)
        bmiCategoryText.text = getText(bmiCategoryTextMap.getOrDefault(category, R.string.unknown))
        bmiCategoryText.setTextColor(getColor(BmiCalculator.getBmiColor(bmi)))

        setupDetails(category)
    }

    private fun setupDetails(category: BmiCategory?) {
        val detailsText = findViewById<TextView>(R.id.bmiDetails)
        val tipText1 = findViewById<TextView>(R.id.bmiTip1)
        val tipText2 = findViewById<TextView>(R.id.bmiTip2)
        val tipText3 = findViewById<TextView>(R.id.bmiTip3)

        detailsText.text = getText(bmiDetailsTextMap.getOrDefault(category, R.string.unknown))

        val tips = bmiTipsTextMap[category]
        tipText1.text = tips?.get(0) ?: getText(R.string.unknown)
        tipText2.text = tips?.get(1) ?: getText(R.string.unknown)
        tipText3.text = tips?.get(2) ?: getText(R.string.unknown)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}