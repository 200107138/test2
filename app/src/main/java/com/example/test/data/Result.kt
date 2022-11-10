package com.example.test

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "result_table")
data class Result(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val time: String,
    val date: String,
)