package edu.pwr.s266867.bmicalculator

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CalculationEntryDao {
    @Insert
    fun insert(entry: BmiEntry)

    @Query("SELECT * FROM bmi_history")
    fun getAllEntries(): Array<BmiEntry>

    @Query("DELETE FROM bmi_history")
    fun clear()

    @Query("SELECT * FROM bmi_history ORDER BY date DESC LIMIT :limit")
    fun getRecentEntries(limit: Int): Array<BmiEntry>
}