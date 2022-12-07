package com.example.test.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "result_table")
data class ResultDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val time: Int,
    val date: String,
    val type: Type,
    val mode: Mode

)

data class Result(
    val id: Int,
    val time: Int,
    val date: String,
    val type: Type,
    val mode: Mode,
    val index: Int = 0
)

object ResultMapper {
    fun mapToResult(dbModel: ResultDbModel, index: Int = 0): Result {
        return Result(
            id = dbModel.id,
            time = dbModel.time,
            date = dbModel.date,
            type = dbModel.type,
            mode = dbModel.mode,
            index = index,
        )
    }

    fun mapToDbModel(dbModel: Result): ResultDbModel {
        return ResultDbModel(
            id = dbModel.id,
            time = dbModel.time,
            date = dbModel.date,
            type = dbModel.type,
            mode = dbModel.mode
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
enum class Mode {
    Training,
    Rating
}