package edu.pwr.s266867.bmicalculator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import edu.pwr.s266867.bmicalculator.Util.roundToDecimal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalculationEntryAdapter : RecyclerView.Adapter<CalculationEntryAdapter.ViewHolder>() {

    private var entries: Array<BmiEntry> = arrayOf()
    private var units: Units = Units.METRIC

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.dateCalcEntryText)
        val weightTextView: TextView = itemView.findViewById(R.id.weightCalcEntryText)
        val heightTextView: TextView = itemView.findViewById(R.id.heightCalcEntryText)
        val bmiValueTextView: TextView = itemView.findViewById(R.id.bmiValueCalcEntryText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val entryView = LayoutInflater.from(parent.context).inflate(R.layout.calculation_entry_view, parent, false)
        return ViewHolder(entryView)
    }

    override fun getItemCount(): Int = entries.size

    private fun formatMeasurement(value: Double, measurement: Measurement, context: Context) =
        "%s %s".format((value * UnitConverter.getConversion(measurement, Units.METRIC, units))
            .roundToDecimal(1).toString(), context.getString(Util.getUnitText(measurement, units)))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = entries[position]

        holder.dateTextView.text = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(Date(entry.date))
        holder.weightTextView.text = formatMeasurement(entry.weight, Measurement.WEIGHT, holder.weightTextView.context)
        holder.heightTextView.text = formatMeasurement(entry.height, Measurement.HEIGHT, holder.heightTextView.context)

        val bmi = entry.bmi
        holder.bmiValueTextView.text = bmi.roundToDecimal(1).toString()
        holder.bmiValueTextView.setTextColor(getColor(holder.bmiValueTextView.context, BmiCalculator.getBmiColor(bmi)))
    }

    fun submitEntries(entries: Array<BmiEntry>) {
        this.entries = entries
        notifyDataSetChanged()  // TODO: Use other notifiers instead
    }

    fun setUnits(units: Units) {
        this.units = units
    }
}