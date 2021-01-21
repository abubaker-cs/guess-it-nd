package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Database & Logic Processor (ViewModel)
 */

class GameViewModel : ViewModel() {

    // The current word
    // var word = ""
    var word = MutableLiveData<String>()

    // The current score
    // var score = 0
    // MutableLiveData = Variable whose value can be changed,
    // it is also a generic class; so we need to define the DATA Type
    // Its default value will be always null
    var score = MutableLiveData<Int>()

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        Log.i("GameViewModel", "GameViewModel Created")

        resetList()
        nextWord()

        score.value = 0 // Default value during initalization
    }

    override fun onCleared() {
        super.onCleared()
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
        //Select and remove a word from the list.
        if (wordList.isEmpty()) {
            // gameFinished()
        } else {
            word.value = wordList.removeAt(0)
        }
    }

    /** Methods for buttons presses **/
    fun onSkip() {
        // score--
        // ()? = null check
        score.value = (score.value)?.minus(1) // score--
        nextWord()
    }

    fun onCorrect() {
        // score++
        score.value = (score.value)?.plus(1) // score++
        nextWord()
    }

}