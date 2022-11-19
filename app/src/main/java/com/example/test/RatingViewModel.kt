package com.example.test

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import androidx.lifecycle.*
import com.example.test.data.Result
import com.example.test.data.ResultDatabase
import com.example.test.data.ResultRepository
import com.example.test.data.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat


class RatingViewModel(application: Application): AndroidViewModel(application){
    fun getNextNavDestination(): Int {
        if (GameSettingsRepository.getInstance().remainingDestinations.isEmpty()) {
            GameSettingsRepository.getInstance().remainingDestinations.addAll(GameSettingsRepository.getInstance().destinations)
            // or whatever logic you want to do when all destinations have been used
        }
        val destination = GameSettingsRepository.getInstance().remainingDestinations.random()
        GameSettingsRepository.getInstance().remainingDestinations.remove(destination)
        return destination
    }
    fun fillDestinations(){
        GameSettingsRepository.getInstance().remainingDestinations =  mutableListOf<Int>().apply { addAll(GameSettingsRepository.getInstance().destinations) }
    }


}
