package com.example.test

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import com.example.test.databinding.FragmentFirstGameBinding

class FirstGameViewModel : ViewModel(){


    private var _currentFirstGameCount = 0
    val currentFirstGameCount: Int
        get() = _currentFirstGameCount

    private var _currentTag = "white"
    val currentTag: String
        get() = _currentTag

    private var _start = 0.000
    val start: Double
        get() = _start

    private var _end = 0.000
    val end: Double
        get() = _end

    private var _reactiontime = 0.000
    val reactiontime: Double
        get() = _reactiontime

    private var _clicked = false
    val clicked: Boolean
        get() = _clicked


    private fun getNextGame() {


        if(_currentTag == "green") {
            _reactiontime = end - start
            _currentTag = "white"
            ++_currentFirstGameCount

        }
        else{
            _reactiontime = 1000.0
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
        _start = System.currentTimeMillis().toDouble()
    }
    fun endtimer(){
        _end = System.currentTimeMillis().toDouble()
    }
    fun reinitializeData() {
        _currentFirstGameCount = 0
        _start = 0.000
        _currentTag = "white"
        _end = 0.000
        _reactiontime = 0.000
    }

    fun nextGame(): Boolean {
        return if (_currentFirstGameCount < MAX_NO_OF_WORDS) {
            getNextGame()

            true
        } else false
    }
}
