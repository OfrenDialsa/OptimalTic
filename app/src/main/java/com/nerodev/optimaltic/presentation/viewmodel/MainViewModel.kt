package com.nerodev.optimaltic.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.nerodev.optimaltic.domain.model.Difficulty
import com.nerodev.optimaltic.domain.model.GameState

interface MainViewModel {
    val state: LiveData<GameState>
    val oWins: LiveData<Int>
    val xWins: LiveData<Int>
    val draws: LiveData<Int>
    val difficulty: LiveData<Difficulty>

    fun makeMove(index: Int)
    fun restartGame()
    fun resetGame()
    fun setDifficulty(newDifficulty: Difficulty)
}