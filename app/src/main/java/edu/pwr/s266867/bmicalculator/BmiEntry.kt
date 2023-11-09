package edu.pwr.s266867.bmicalculator

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bmi_history")
data class BmiEntry(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "weight")
    val weight: Double,

    @ColumnInfo(name = "height")
    val height: Double,

    @ColumnInfo(name = "bmi")
    val bmi: Double,

    @ColumnInfo(name = "date")
    val date: Long
)
