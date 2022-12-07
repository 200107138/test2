package com.example.test

import android.app.Application
import androidx.lifecycle.*
import com.example.test.data.*


class RatingViewModel(application: Application): AndroidViewModel(application){

    val resultDao = ResultDatabase.getDatabase(application).resultDao()
    var sum = 0
    private val repository: ResultRepository = ResultRepository(resultDao)

    val readAdllData = resultDao.readAlllData(Type.ReactionTime)
    fun avgReactionTime(){
        sum = readAdllData.value?.sum()!! / readAdllData.value!!.size
    }


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
