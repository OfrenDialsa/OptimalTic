package com.nerodev.optimaltic.domain.repository

import com.nerodev.optimaltic.domain.model.Difficulty
import com.nerodev.optimaltic.domain.model.GameState
import com.nerodev.optimaltic.domain.model.Player

interface GameRepository {
    suspend fun makeMove(index: Int, gameState: GameState): GameState
    suspend fun getBotMove(board: List<Player>, difficulty: Difficulty): Int
    fun checkWinner(board: List<Player>): Player?
    fun setDifficulty(difficulty: Difficulty)
    fun restartGame(): GameState
}