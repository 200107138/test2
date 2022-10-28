package com.example.test

import androidx.lifecycle.ViewModel
import com.example.android.unscramble.ui.game.MAX_NO_OF_WORDS

class FirstGameViewModel : ViewModel(){
    private var _score = 0
    val score: Int
        get() = _score

    private var _currentFirstGameCount = 0
    val currentFirstGameCount: Int
        get() = _currentFirstGameCount

    private lateinit var _currentTag: String
    val currentTag: String
        get() = _currentTag

    private fun getNextWord() {

            ++_currentWordCount
            wordsList.add(currentWord)

    }


    fun nextWord(): Boolean {
        return if (_currentFirstGameCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }
}