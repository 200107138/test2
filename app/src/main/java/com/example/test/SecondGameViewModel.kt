package com.example.test

import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class SecondGameViewModel : ViewModel(){
    private lateinit var timer: CountDownTimer
    private lateinit var timer2: CountDownTimer

    private val _averagereactiontime = MutableLiveData(0)
    val averagereactiontime: LiveData<Int>
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

    private val _starttext = MutableLiveData<String>("Start")
    val starttext: LiveData<String>
        get() = _starttext

    private var _clicked = MutableLiveData<Boolean>(false)
    val clicked: LiveData<Boolean>
        get() = _clicked

    private fun getNextGame() {
        _clicked.value = true
        _starttext.value = ""
        timer()
        timer.start()
    }
    fun button1clicked(){

        if(_randomfirstnumber != 0 && _randomsecondnumber != 0){
            if (randomfirstnumber > randomsecondnumber){
                _end = System.currentTimeMillis().toInt()
                _randomfirstnumber = 0
                _randomsecondnumber = 0
                _currentSecondGameCount.value = (_currentSecondGameCount.value)?.inc()
                _reactiontime = end - start
                _averagereactiontime.value =
                    _averagereactiontime.value?.plus((_reactiontime / ROUNDS))
                timer()
                timer.start()
            }
            else{
                _randomfirstnumber = 0
                _randomsecondnumber = 0
                _currentSecondGameCount.value = (_currentSecondGameCount.value)?.inc()
                _reactiontime = 1000
                _averagereactiontime.value =
                    _averagereactiontime.value?.plus((_reactiontime / ROUNDS))
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
                _currentSecondGameCount.value = (_currentSecondGameCount.value)?.inc()
                _reactiontime = end - start
                _averagereactiontime.value =
                    _averagereactiontime.value?.plus((_reactiontime / ROUNDS))
                timer()
                timer.start()
            }
            else{
                _randomfirstnumber = 0
                _randomsecondnumber = 0
                _currentSecondGameCount.value = (_currentSecondGameCount.value)?.inc()
                _reactiontime = 1000
                _averagereactiontime.value =
                    _averagereactiontime.value?.plus((_reactiontime / ROUNDS))
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
        _clicked.value = false
        _currentSecondGameCount.value = 1
        _start = 0
        _end = 0
        _firstnumber.value = 0
        _secondnumber.value = 0
        _randomfirstnumber = 0
        _randomsecondnumber = 0
        _reactiontime = 0
        _averagereactiontime.value = 0
        _starttext.value = "Start"
    }

    fun nextGame1(): Boolean {
        return if(_currentSecondGameCount.value!! < ROUNDS) {
            button1clicked()
            true
        }else false

    }
    fun nextGame2(): Boolean {

        return if(_currentSecondGameCount.value!! < ROUNDS) {
            button2clicked()
            true
        }else false
    }

    fun startGame(): Boolean {
        getNextGame()
        return _currentSecondGameCount.value!! < ROUNDS
    }
}
