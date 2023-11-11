package edu.pwr.s266867.bmicalculator

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel : ViewModel() {
    val entries = MutableLiveData<Array<BmiEntry>>()

    fun loadEntries(context: Context, limit: Int = 10) {
        viewModelScope.launch {
            entries.value = withContext(Dispatchers.IO) {
                AppDatabase.getDatabase(context).calculationEntryDao().getRecentEntries(limit)
            }
        }
    }
}