package com.nerodev.optimaltic.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerodev.optimaltic.data.Difficulty
import com.nerodev.optimaltic.data.GameState
import com.nerodev.optimaltic.data.Player
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _state = MutableLiveData(GameState())
    val state: LiveData<GameState> = _state

    private val _oWins = MutableLiveData(0)
    val oWins: LiveData<Int> = _oWins

    private val _xWins = MutableLiveData(0)
    val xWins: LiveData<Int> = _xWins

    private val _draws = MutableLiveData(0)
    val draws: LiveData<Int> = _draws

    private val _difficulty = MutableLiveData(Difficulty.Standard)
    val difficulty: LiveData<Difficulty> = _difficulty

    fun makeMove(index: Int) {
        val currentState = _state.value ?: return

        if (currentState.board[index] == Player.NONE && !currentState.isGameOver) {
            val newBoard = currentState.board.toMutableList()
            newBoard[index] = currentState.currentPlayer
            val winner = checkWinner(newBoard)

            _state.value = currentState.copy(
                board = newBoard,
                currentPlayer = if (currentState.currentPlayer == Player.X) Player.O else Player.X,
                isGameOver = winner != null || newBoard.none { it == Player.NONE },
                winner = winner
            )

            if (_state.value?.isGameOver == true) {
                when (winner) {
                    Player.O -> _oWins.value = (_oWins.value ?: 0) + 1
                    Player.X -> _xWins.value = (_xWins.value ?: 0) + 1
                    else -> _draws.value = (_draws.value ?: 0) + 1
                }
            }

            if (_state.value?.currentPlayer == Player.O && !_state.value?.isGameOver!!) {
                botMove()
            }
        }
    }

    private fun botMove() {
        viewModelScope.launch {
            delay(1000L)

            val currentState = _state.value ?: return@launch
            val bestMove = when (_difficulty.value ?: Difficulty.Standard) {
                Difficulty.Impossible -> findBestMove(currentState.board) // Minimax AI
                Difficulty.Standard -> findRandomMove(currentState.board) // Random move
            }

            if (bestMove != -1) {
                makeMove(bestMove)
            }
        }
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

    private fun checkWinner(board: List<Player>): Player? {
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

    fun restartGame() {
        _state.value = GameState()
    }

    fun setDifficulty(newDifficulty: Difficulty) {
        _difficulty.value = newDifficulty
        resetGame()
    }

    fun resetGame() {
        _state.value = GameState()
        _oWins.value = 0
        _xWins.value = 0
        _draws.value = 0
    }
}