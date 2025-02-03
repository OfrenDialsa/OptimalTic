package com.nerodev.optimaltic.data.repository

import com.nerodev.optimaltic.domain.model.Difficulty
import com.nerodev.optimaltic.domain.model.GameState
import com.nerodev.optimaltic.domain.model.Player
import com.nerodev.optimaltic.domain.repository.GameRepository

class GameRepositoryImpl : GameRepository {
    private var difficulty: Difficulty = Difficulty.Normal

    override suspend fun makeMove(index: Int, gameState: GameState): GameState {
        if (gameState.board[index] != Player.NONE || gameState.isGameOver) {
            return gameState
        }

        val newBoard = gameState.board.toMutableList()
        newBoard[index] = gameState.currentPlayer

        val winner = checkWinner(newBoard)
        val isGameOver = winner != null || newBoard.none { it == Player.NONE }

        return gameState.copy(
            board = newBoard,
            currentPlayer = if (gameState.currentPlayer == Player.X) Player.O else Player.X,
            isGameOver = isGameOver,
            winner = winner
        )
    }

    override suspend fun getBotMove(board: List<Player>, difficulty: Difficulty): Int {
        return when (difficulty) {
            Difficulty.Normal -> findRandomMove(board)
            Difficulty.Impossible -> findBestMove(board)
            Difficulty.Multiplayer -> -1
        }
    }

    override fun checkWinner(board: List<Player>): Player? {
        val winPatterns = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
            listOf(0, 4, 8), listOf(2, 4, 6)
        )

        for (pattern in winPatterns) {
            val (a, b, c) = pattern
            if (board[a] != Player.NONE && board[a] == board[b] && board[b] == board[c]) {
                return board[a]
            }
        }
        return null
    }

    override fun setDifficulty(difficulty: Difficulty) {
        this.difficulty = difficulty
    }

    override fun restartGame(): GameState {
        return GameState()
    }

    private fun findRandomMove(board: List<Player>): Int {
        val availableMoves = board.indices.filter { board[it] == Player.NONE }
        return if (availableMoves.isNotEmpty()) availableMoves.random() else -1
    }

    private fun findBestMove(board: List<Player>): Int {
        var bestScore = Int.MIN_VALUE
        var bestMove = -1

        for (i in board.indices) {
            if (board[i] == Player.NONE) {
                val newBoard = board.toMutableList()
                newBoard[i] = Player.O

                val score = minimax(newBoard, 0, false)
                if (score > bestScore) {
                    bestScore = score
                    bestMove = i
                }
            }
        }
        return bestMove
    }

    private fun minimax(board: List<Player>, depth: Int, isMaximizing: Boolean): Int {
        val winner = checkWinner(board)
        if (winner == Player.X) return -10 + depth
        if (winner == Player.O) return 10 - depth
        if (board.none { it == Player.NONE }) return 0

        return if (isMaximizing) {
            var bestScore = Int.MIN_VALUE
            for (i in board.indices) {
                if (board[i] == Player.NONE) {
                    val newBoard = board.toMutableList()
                    newBoard[i] = Player.O
                    val score = minimax(newBoard, depth + 1, false)
                    bestScore = maxOf(bestScore, score)
                }
            }
            bestScore
        } else {
            var bestScore = Int.MAX_VALUE
            for (i in board.indices) {
                if (board[i] == Player.NONE) {
                    val newBoard = board.toMutableList()
                    newBoard[i] = Player.X
                    val score = minimax(newBoard, depth + 1, true)
                    bestScore = minOf(bestScore, score)
                }
            }
            bestScore
        }
    }
}