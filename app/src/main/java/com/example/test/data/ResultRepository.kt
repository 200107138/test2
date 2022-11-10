package com.example.test

import androidx.lifecycle.LiveData

class ResultRepository(private val resultDao: ResultDao) {

    val readAllData: LiveData<List<Result>> = resultDao.readAllData()

    suspend fun addResult(result: Result){
        resultDao.addResult(result)
    }
    suspend fun deleteResult(result: Result){
        resultDao.deleteResult(result)
    }

}