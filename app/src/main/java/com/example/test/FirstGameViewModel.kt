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

    private var _averagereactiontime = 0
    val averagereactiontime: Int
        get() = _averagereactiontime

    private val _currentFirstGameCount = MutableLiveData(1)
    val currentFirstGameCount: LiveData<Int>
        get() = _currentFirstGameCount

    private var _currentTag = "white"
    val currentTag: String
        get() = _currentTag

    private var _start = 0
    val start: Int
        get() = _start

    private var _end = 0
    val end: Int
        get() = _end

    private var _reactiontime = 0
    val reactiontime: Int
        get() = _reactiontime

    private var _clicked = false
    val clicked: Boolean
        get() = _clicked

    private val _starttext = MutableLiveData<String>("Start")
    val starttext: LiveData<String>
        get() = _starttext

    private val _background = MutableLiveData<Boolean>()
    val background: LiveData<Boolean>
        get() = _background

    private fun getNextGame() {


        if(_currentTag == "green") {
            _currentTag = "white"
            _background.value = false
            timer.cancel()
            timer()
            timer.start()
            _end = System.currentTimeMillis().toInt()
            _reactiontime = end - start



            _averagereactiontime += (_reactiontime / ROUNDS)
            _currentFirstGameCount.value = (_currentFirstGameCount.value)?.inc()
            if(_currentFirstGameCount.value!! > ROUNDS){
                timer.cancel()
            }

        }
        else if(_currentTag == "white" && !clicked){
            _clicked = !_clicked
            timer()
            timer.start()
            _starttext.value = ""

        }
        else if(_currentTag == "white" && clicked) {
            _background.value = false
            timer.cancel()
            timer()
            timer.start()
            _reactiontime = 1000

            _averagereactiontime += (_reactiontime / ROUNDS)
            _currentFirstGameCount.value = (_currentFirstGameCount.value)?.inc()
            if(_currentFirstGameCount.value!! > ROUNDS){
                timer.cancel()
            }

        }

    }
    fun timer(){
        timer = object : CountDownTimer(5000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                if(_currentTag == "white"){
                    _currentTag = "green"
                    _start = System.currentTimeMillis().toInt()
                    _background.value = true
                }
            }
        }
    }




    fun reinitializeData() {
        _currentFirstGameCount.value = 1
        _start = 0
        _currentTag = "white"
        _end = 0
        _background.value = false
        _clicked = false
        _starttext.value = "Start"
        _reactiontime = 0
        _averagereactiontime = 0
    }
    private fun finalresult(){
        val result = Result(0, "$_averagereactiontime milliseconds", convertLongToDateString(System.currentTimeMillis()), Type.ReactionTime)
        // Add Data to Database
        addResult(result)
    }
    @SuppressLint("SimpleDateFormat")
    fun convertLongToDateString(systemTime: Long): String {
        return SimpleDateFormat("HH:mm:ss'  'MMM.dd.yyyy")
            .format(systemTime).toString()
    }


    fun nextGame(): Boolean {
       return if( _currentFirstGameCount.value!! < ROUNDS) {
           getNextGame()
           true
       }
        else{
            finalresult()
            false
        }
    }
}
