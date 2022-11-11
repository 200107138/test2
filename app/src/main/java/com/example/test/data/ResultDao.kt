package com.example.test.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.test.data.Result

@Dao
interface ResultDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addResult(result: Result)

    @Delete
    suspend fun deleteResult(result: Result)

    @Query("SELECT * FROM result_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Result>>

}