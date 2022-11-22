package com.example.test.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "result_table")
data class ResultDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val time: String,
    val date: String,
    val type: Type,
)

data class Result(
    val id: Int,
    val time: String,
    val date: String,
    val type: Type,
    val index: Int = 0,
)

object ResultMapper {
    fun mapToResult(dbModel: ResultDbModel, index: Int = 0): Result {
        return Result(
            id = dbModel.id,
            time = dbModel.time,
            date = dbModel.date,
            type = dbModel.type,
            index = index,
        )
    }

    fun mapToDbModel(dbModel: Result): ResultDbModel {
        return ResultDbModel(
            id = dbModel.id,
            time = dbModel.time,
            date = dbModel.date,
            type = dbModel.type,
        )
    }
}

fun ResultDbModel.toModel(index: Int = 0): Result {
    return ResultMapper.mapToResult(this, index)
}

fun Result.toDbModel(): ResultDbModel {
    return ResultMapper.mapToDbModel(this)
}

enum class Type {
    PeripheralVision,
    ReactionTime,
    Memory
}