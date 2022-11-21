package com.example.test.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "result_table")
data class Result(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val time: String,
    val date: String,
    val type: Type,
) {
    @Ignore
    var index: Int = 0
}

enum class Type {
    PeripheralVision,
    ReactionTime,
    Memory
}