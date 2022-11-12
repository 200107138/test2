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
import com.example.test.data.Result
import com.example.test.data.ResultDatabase
import com.example.test.data.ResultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Collections.shuffle
import kotlin.concurrent.timer


class ThirdGameViewModel(application: Application): AndroidViewModel(application){


    var drawable_array= arrayOf(R.drawable.cell_src_1, R.drawable.cell_src_2, R.drawable.cell_src_3, R.drawable.cell_src_4,
        R.drawable.cell_src_5, R.drawable.cell_src_6, R.drawable.cell_src_7, R.drawable.cell_src_8,
        R.drawable.cell_src_1, R.drawable.cell_src_2, R.drawable.cell_src_3, R.drawable.cell_src_4,
        R.drawable.cell_src_5, R.drawable.cell_src_6, R.drawable.cell_src_7, R.drawable.cell_src_8,
    )
    var drawable_clicked = arrayOf(true, true, true, true,
        true, true, true, true,
        true, true, true, true,
        true, true, true, true,
    )

    private val _list = MutableLiveData(drawable_clicked)


    val list: LiveData<Array<Boolean>>
        get() = _list

    private var _drwbl = R.drawable.cell_src_1
    val drwbl: Int
        get() = _drwbl

    private lateinit var timer: CountDownTimer

    private val _currentThirdGameCount = MutableLiveData(1)
    val currentThirdGameCount: LiveData<Int>
        get() = _currentThirdGameCount

    private var _time = 0
    val time: Int
        get() = _time

    private var _averagetime = 0
    val averagetime: Int
        get() = _averagetime

    private var _start = 0
    val start: Int
        get() = _start

    private var _end = 0
    val end: Int
        get() = _end

    private var _ended = 1
    val ended: Int
        get() = _ended

    private var _pair = 0
    val pair: Int
        get() = _pair

    private var _indexoffirst = 0
    val indexoffirst: Int
        get() = _indexoffirst


    private var _clickcount = 0
    val clickcount: Int
        get() = _clickcount

    private var _clicked = MutableLiveData<Boolean>(false)
    val clicked: LiveData<Boolean>
        get() = _clicked

    private fun getNextGame() {
        _start = System.currentTimeMillis().toInt()
        _clicked.value = true
        drawable_clicked.fill(false)
        _list.value = drawable_clicked
    }
    fun shufflelist(){
        drawable_array.shuffle()
    }
    fun onClick(index: Int){

        if(_ended == 1) {
        drawable_clicked[index] = true
        _list.value = drawable_clicked

            if (drawable_array[index] != 0 && drawable_clicked[index]) {
                _clickcount++
                if (clickcount == 1) {
                    _drwbl = drawable_array[index]
                    _indexoffirst = index
                }
                if (clickcount == 2) {
                    if (drwbl == drawable_array[index]) {
                        _pair++
                        if(pair == 8){
                            nextround()
                            _currentThirdGameCount
                            _end = System.currentTimeMillis().toInt()
                            _time = end - start
                            _averagetime += (_time / ROUNDS)
                        }
                        drawable_array[index] = 0
                        drawable_array[indexoffirst] = 0

                        _clickcount = 0
                    } else {
                        _ended = 0
                        timer(index)
                        timer.start()
                        _clickcount = 0

                    }
                }
            }
        }
    }

    fun timer(index: Int){
        timer = object : CountDownTimer(500, 1000) {

            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {

                    drawable_clicked[index] = false
                    drawable_clicked[indexoffirst] = false
                    _list.value = drawable_clicked
              _ended = 1
            }
        }
    }


    fun getdrawable(index: Int): Int  {
        _drwbl = drawable_array[index]
        return drwbl
    }


    fun reinitializeData() {
        drawable_array.shuffle()
        _clicked.value = false
        drawable_clicked.fill(false)
        _currentThirdGameCount.value = 1
        _start = 0

    }
    fun nextround() {
        drawable_array.shuffle()
        _clicked.value = false
        drawable_clicked.fill(false)
        _list.value = drawable_clicked
        _start = 0
        _end = 0
        _pair = 0

    }

    @SuppressLint("SimpleDateFormat")
    fun convertLongToDateString(systemTime: Long): String {
        return SimpleDateFormat("HH:mm:ss'  'MMM.dd.yyyy")
            .format(systemTime).toString()
    }


    fun startGame(): Boolean {
        getNextGame()
        return _currentThirdGameCount.value!! < ROUNDS
    }
}
