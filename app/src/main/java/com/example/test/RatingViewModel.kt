package com.example.test

import android.app.Application
import androidx.lifecycle.*
import com.example.test.data.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RatingViewModel(application: Application): AndroidViewModel(application){

    val resultDao = ResultDatabase.getDatabase(application).resultDao()
    private val _sum = MutableLiveData<String>("0")
    val sum: LiveData<String>
        get() = _sum




    fun avgReactionTime() {



    }


    fun getNextNavDestination(): Int {
        GameSettingsRepository.getInstance().isRatingModeEnabled = true
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
