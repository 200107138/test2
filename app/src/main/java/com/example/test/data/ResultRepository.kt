package com.example.test.data

import androidx.lifecycle.LiveData
import com.example.test.data.Result
import com.example.test.data.ResultDao

class ResultRepository(private val resultDao: ResultDao) {

//    val readAllData: LiveData<List<Result>> = resultDao.readAllData()

    suspend fun addResult(result: Result){
        resultDao.addResult(result.toDbModel())
    }
    suspend fun deleteResult(result: Result){
        resultDao.deleteResult(result.toDbModel())
    }

}