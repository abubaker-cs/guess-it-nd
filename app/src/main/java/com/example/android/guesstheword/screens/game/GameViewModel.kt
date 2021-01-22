package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

/**
 * Database & Logic Processor (ViewModel)
 */

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

class GameViewModel : ViewModel() {

    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    // These represent different important times
    companion object {

        // This is when the game is over
        const val DONE = 0L

        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L

        // This is the total time of the game
        const val COUNTDOWN_TIME = 30000L // 30sec
    }

    private val timer: CountDownTimer

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    // LiveDATA Call | Lamda Function
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    // The current word
    // var word = ""
    private val _word = MutableLiveData<String>()

    // External
    val word: LiveData<String>
        get() = _word

    // The current score
    // var score = 0
    // MutableLiveData = Variable whose value can be changed,
    // it is also a generic class; so we need to define the DATA Type
    // Its default value will be always null

    // Dynamic - For internal usage
    private val _score = MutableLiveData<Int>()


    // Constant - For external usage
    val score: LiveData<Int>
        get() = _score

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    // Dynamic - Internal
    private val _eventGameFinish = MutableLiveData<Boolean>()

    // Static - External
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    init {
        // Log.i("GameViewModel", "GameViewModel Created")

        _eventGameFinish.value = false
        resetList()
        nextWord()
        _score.value = 0 // Default value during initalization

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                // Done - implement what should happen each tick of the timer
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                // Done - implement what should happen when the timer finishes
                _currentTime.value = DONE
                _eventGameFinish.value = true
            }
        }

        // DateUtils.formatElapsedTime()
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Log.i("GameViewModel", "GameViewModel Destroyed")
    }


    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {

        if (wordList.isEmpty()) {
            resetList()
        }

        _word.value = wordList.removeAt(0)

    }

    /** Methods for buttons presses **/
    fun onSkip() {
        // score--
        // ()? = null check
        _score.value = (score.value)?.minus(1) // score--
        nextWord()
    }

    fun onCorrect() {
        // score++
        _score.value = (score.value)?.plus(1) // score++
        nextWord()
    }

    fun onGameFinishComplete() {
        // Reset the value on Game Finish
        _eventGameFinish.value = false
    }

}