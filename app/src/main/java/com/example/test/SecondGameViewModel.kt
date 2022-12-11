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


class SecondGameViewModel(application: Application): AndroidViewModel(application){

    private val repository: ResultRepository

    init {
        val resultDao = ResultDatabase.getDatabase(application).resultDao()
        repository = ResultRepository(resultDao)
    }

    fun addResult(result: Result){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addResult(result)
        }
    }


    private lateinit var timer: CountDownTimer
    private lateinit var timer2: CountDownTimer

    private var _averagereactiontime = 0
    val averagereactiontime: Int
        get() = _averagereactiontime

    private val _currentSecondGameCount = MutableLiveData(1)
    val currentSecondGameCount: LiveData<Int>
        get() = _currentSecondGameCount

    private var _firstnumber = MutableLiveData(0)
    val firstnumber: LiveData<Int>
        get() = _firstnumber

    private var _secondnumber = MutableLiveData(0)
    val secondnumber: LiveData<Int>
        get() = _secondnumber

    private var _randomfirstnumber = 0
    val randomfirstnumber: Int
        get() = _randomfirstnumber

    private var _randomsecondnumber = 0
    val randomsecondnumber: Int
        get() = _randomsecondnumber


    private var _reactiontime = 0
    val reactiontime: Int
        get() = _reactiontime

    private var _start = 0
    val start: Int
        get() = _start

    private var _end = 0
    val end: Int
        get() = _end


    fun getNextGame() {

        timer()
        timer.start()
    }
    fun button1clicked(){
        if(_randomfirstnumber != 0 && _randomsecondnumber != 0){
            if (randomfirstnumber > randomsecondnumber){
                _end = System.currentTimeMillis().toInt()
                _randomfirstnumber = 0
                _randomsecondnumber = 0
                _reactiontime = end - start
                _averagereactiontime += _reactiontime
                _currentSecondGameCount.value = (_currentSecondGameCount.value)?.inc()
                timer()
                timer.start()
            }
            else{
                _randomfirstnumber = 0
                _randomsecondnumber = 0
                _reactiontime = 1000
                _averagereactiontime += _reactiontime
                _currentSecondGameCount.value = (_currentSecondGameCount.value)?.inc()
                timer()
                timer.start()
            }
        }
    }
    fun button2clicked(){

        if(_randomfirstnumber != 0 && _randomsecondnumber != 0){
            if (randomsecondnumber > randomfirstnumber){
                _end = System.currentTimeMillis().toInt()
                _randomfirstnumber = 0
                _randomsecondnumber = 0
                _reactiontime = end - start
                _averagereactiontime += _reactiontime
                _currentSecondGameCount.value = (_currentSecondGameCount.value)?.inc()
                timer()
                timer.start()
            }
            else{
                _randomfirstnumber = 0
                _randomsecondnumber = 0
                _reactiontime = 1000
                _averagereactiontime += _reactiontime
                _currentSecondGameCount.value = (_currentSecondGameCount.value)?.inc()
                timer()
                timer.start()
            }
        }
    }
    fun timer(){
        timer = object : CountDownTimer(3000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                _start = System.currentTimeMillis().toInt()
                _randomfirstnumber = (10..99).random()
                _randomsecondnumber = (10..99).random()
                _firstnumber.value = _randomfirstnumber
                _secondnumber.value = _randomsecondnumber

                timer2()
                timer2.start()
            }
        }
    }
    fun timer2(){
        timer2 = object : CountDownTimer(500, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                _firstnumber.value = 0
                _secondnumber.value = 0

            }
        }
    }



    fun reinitializeData() {

        _currentSecondGameCount.value = 1
        _start = 0
        _end = 0
        _firstnumber.value = 0
        _secondnumber.value = 0
        _randomfirstnumber = 0
        _randomsecondnumber = 0
        _reactiontime = 0
        _averagereactiontime = 0

    }
    fun getNextNavDestination(): Int {
        if (GameSettingsRepository.getInstance().remainingDestinations.isEmpty()) {
            for (i in GameSettingsRepository.getInstance().gameResults) {
                addResult(i)
            }
            return R.id.fragment_rating
            // or whatever logic you want to do when all destinations have been used
        }
        val destination = GameSettingsRepository.getInstance().remainingDestinations.random()
        GameSettingsRepository.getInstance().remainingDestinations.remove(destination)
        return destination
    }
    fun changerounds(){
        if(GameSettingsRepository.getInstance().isRatingModeEnabled == true){
            ROUNDS = 1
        }
        if(GameSettingsRepository.getInstance().isRatingModeEnabled == false){
            ROUNDS = 3
        }
    }
    fun finalresult(){
        _averagereactiontime = _averagereactiontime / ROUNDS
        if(GameSettingsRepository.getInstance().isRatingModeEnabled == false){
            val result = Result(0, _averagereactiontime, convertLongToDateString(System.currentTimeMillis()), Type.PeripheralVision, Mode.Training)
            // Add Data to Database
            addResult(result)
        }
        else{
            val result = Result(0, _averagereactiontime, convertLongToDateString(System.currentTimeMillis()), Type.PeripheralVision, Mode.Rating)
            // Add Data to Database
            GameSettingsRepository.getInstance().gameResults.add(result)
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun convertLongToDateString(systemTime: Long): String {
        return SimpleDateFormat("HH:mm:ss'  'MMM.dd.yyyy")
            .format(systemTime).toString()
    }

}
