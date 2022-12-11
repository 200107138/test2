package com.example.test

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.*
import com.example.test.ROUNDS
import com.example.test.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class HistoryViewModel(
    application: Application,
    private val type: Type,
) : AndroidViewModel(application) {
    val resultDao = ResultDatabase.getDatabase(application).resultDao()
    private val repository: ResultRepository = ResultRepository(resultDao)
    val readAllData = resultDao.readAllData(type, Mode.Training).map { dbResults ->
        dbResults.mapIndexed { index, dbModel ->
            dbModel.toModel(index = index + 1)
        }
    }


    fun deleteUser(result: Result) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteResult(result)
        }

    }


}
