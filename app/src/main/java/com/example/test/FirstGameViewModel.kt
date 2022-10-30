package com.example.test

import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class FirstGameViewModel : ViewModel(){
    private lateinit var timer: CountDownTimer

    private val _averagereactiontime = MutableLiveData(0)
    val averagereactiontime: LiveData<Int>
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
            _clicked = !_clicked
            timer()
            timer.cancel()
            _end = System.currentTimeMillis().toInt()
            _reactiontime = end - start
            _currentTag = "white"
            _starttext.value = "Start"
            _background.value = false
            _averagereactiontime.value = _averagereactiontime.value?.plus((_reactiontime / MAX_NO_OF_WORDS))
            _currentFirstGameCount.value = (_currentFirstGameCount.value)?.inc()


        }
        else if(_currentTag == "white" && !clicked){
            _clicked = !_clicked
            timer()
            timer.start()

            _starttext.value = ""

        }
        else if(_currentTag == "white" && clicked) {
            timer()
            timer.cancel()
            _end = System.currentTimeMillis().toInt()
            _clicked = !_clicked
            _reactiontime = 1000
            _starttext.value = "Start"
            _background.value = false
            _averagereactiontime.value =
                _averagereactiontime.value?.plus((_reactiontime / MAX_NO_OF_WORDS))
            _currentFirstGameCount.value = (_currentFirstGameCount.value)?.inc()

        }

    }
    fun timer(){
        timer = object : CountDownTimer(5000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                if(_currentTag == "white" && clicked){
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
        _reactiontime = 0
        _averagereactiontime.value = 0
    }

    fun nextGame(): Boolean {
        getNextGame()
        return _currentFirstGameCount.value!! < MAX_NO_OF_WORDS
    }
}
