package com.example.test

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ResultDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addResult(result: Result)

    @Delete
    suspend fun deleteResult(result: Result)

    @Query("SELECT * FROM result_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Result>>

}