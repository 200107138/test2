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


class FirstGameViewModel(application: Application): AndroidViewModel(application){

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
    private lateinit var timerforpenalty: CountDownTimer

    private var _averagereactiontime = 0
    val averagereactiontime: Int
        get() = _averagereactiontime

    private val _currentFirstGameCount = MutableLiveData(1)
    val currentFirstGameCount: LiveData<Int>
        get() = _currentFirstGameCount

    private var _start = 0
    val start: Int
        get() = _start

    private var _end = 0
    val end: Int
        get() = _end

    private var _randommillisecond = 2000L
    val randommillisecond: Long
        get() = _randommillisecond

    private var _reactiontime = 0
    val reactiontime: Int
        get() = _reactiontime


    private val _penaltylayout = MutableLiveData<Boolean>(false)
    val penaltylayout: LiveData<Boolean>
        get() = _penaltylayout

    private val _golayout = MutableLiveData<Boolean>(true)
    val golayout: LiveData<Boolean>
        get() = _golayout

    private val _green = MutableLiveData<Boolean>(false)
    val green: LiveData<Boolean>
        get() = _green

    private fun getNextGame() {
        timer.cancel()
        if(_green.value == true) {
            _end = System.currentTimeMillis().toInt()
            _reactiontime += (_end - _start)
            _currentFirstGameCount.value = (_currentFirstGameCount.value)?.inc()
            _green.value = false

                _golayout.value = true

            _randommillisecond = 2000L
            timer.start()
        }
        else {
            _reactiontime += 1000
            timer.cancel()
            _currentFirstGameCount.value = (_currentFirstGameCount.value)?.inc()

    _penaltylayout.value = true

            timerforpenalty.start()
        }

    }
    fun starttimer(){
        _golayout.value = true
        timer()
        timer.start()
    }
    fun timer(){

        timer = object : CountDownTimer(_randommillisecond, 100) {

            override fun onTick(millisUntilFinished: Long) {
                if(_randommillisecond - 500 > millisUntilFinished){
                    _golayout.value = false
                }
            }

            override fun onFinish() {
                if(_green.value == false){
                    _green.value = true
                }
                _start =System.currentTimeMillis().toInt()
            }
        }
        timerforpenalty = object : CountDownTimer(2000, 1000) {

            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
               _penaltylayout.value = false
                _golayout.value = true
                _randommillisecond = 2000L
                timer.start()

            }
        }
    }
    fun changerounds(){
        if(GameSettingsRepository.getInstance().isRatingModeEnabled == true){
            ROUNDS = 1
        }
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

    fun reinitializeData() {
        _currentFirstGameCount.value = 1
        _reactiontime = 0
        _averagereactiontime = 0

    }
    fun finalresult(){
        _averagereactiontime = reactiontime / ROUNDS
        val result = Result(0, "$_averagereactiontime milliseconds", convertLongToDateString(System.currentTimeMillis()), Type.ReactionTime)
        // Add Data to Database
        addResult(result)
    }
    @SuppressLint("SimpleDateFormat")
    fun convertLongToDateString(systemTime: Long): String {
        return SimpleDateFormat("HH:mm:ss'  'MMM.dd.yyyy")
            .format(systemTime).toString()
    }

    fun onStart() {
        getNextGame()
    }

}
