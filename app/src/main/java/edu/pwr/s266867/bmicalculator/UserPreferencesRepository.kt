package edu.pwr.s266867.bmicalculator

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userPreferences")
    private val dataStore = context.dataStore

    companion object {
        private val UNITS_KEY = stringPreferencesKey("units")
    }

    fun getUnits(): Flow<Units> = context.dataStore.data.map { prefs -> Units.valueOf(prefs[UNITS_KEY] ?: Units.METRIC.toString()) }

    suspend fun setUnits(units: Units) {
        dataStore.edit { prefs ->
            prefs[UNITS_KEY] = units.toString()
        }
    }
}