package com.example.test

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import com.example.test.databinding.FragmentFirstGameBinding

class FirstGameViewModel : ViewModel(){

    private var _averagereactiontime = 0
    val averagereactiontime: Int
        get() = _averagereactiontime
    private var _currentFirstGameCount = 0
    val currentFirstGameCount: Int
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


    private fun getNextGame() {


        if(_currentTag == "green") {
            _reactiontime = end - start
            _currentTag = "white"
            _averagereactiontime += (_reactiontime / MAX_NO_OF_WORDS)
            ++_currentFirstGameCount

        }
        else{
            _reactiontime = 1000
            _averagereactiontime += (_reactiontime / MAX_NO_OF_WORDS)
            ++_currentFirstGameCount
        }

    }
    fun setgreen(){
        _currentTag = "green"
    }
    fun clicked(){
        _clicked = !_clicked
    }
    fun starttimer(){
        _start = System.currentTimeMillis().toInt()
    }
    fun endtimer(){
        _end = System.currentTimeMillis().toInt()
    }
    fun reinitializeData() {
        _currentFirstGameCount = 0
        _start = 0
        _currentTag = "white"
        _end = 0
        _reactiontime = 0
        _averagereactiontime = 0
    }

    fun nextGame(): Boolean {
        return if (_currentFirstGameCount < MAX_NO_OF_WORDS) {
            getNextGame()

            true
        } else false
    }
}
