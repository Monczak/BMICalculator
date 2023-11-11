package edu.pwr.s266867.bmicalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity() {

    private lateinit var viewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        viewModel = ViewModelProvider(this)[HistoryViewModel::class.java]

        val toolbar = findViewById<Toolbar>(R.id.toolbar3)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setupUI()
    }

    private fun setupUI() {
        val entryRecyclerView = findViewById<RecyclerView>(R.id.bmiEntryRecyclerView)
        val adapter = CalculationEntryAdapter()
        entryRecyclerView.adapter = adapter
        entryRecyclerView.layoutManager = LinearLayoutManager(this)

        val unitsStr = intent.getStringExtra("units")
        val units = if (unitsStr != null) Units.valueOf(unitsStr) else Units.METRIC
        adapter.setUnits(units)

        viewModel.entries.observe(this) { entries ->
            adapter.submitEntries(entries)
        }
        viewModel.loadEntries(this)
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