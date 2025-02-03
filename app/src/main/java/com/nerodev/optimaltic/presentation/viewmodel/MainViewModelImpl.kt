package com.nerodev.optimaltic.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerodev.optimaltic.domain.model.Difficulty
import com.nerodev.optimaltic.domain.model.GameState
import com.nerodev.optimaltic.domain.model.Player
import com.nerodev.optimaltic.domain.usecase.botmoveusecase.GetBotMoveUseCase
import com.nerodev.optimaltic.domain.usecase.checkwinnerusecase.CheckWinnerUseCase
import com.nerodev.optimaltic.domain.usecase.makemoveusecase.MakeMoveUseCase
import com.nerodev.optimaltic.domain.usecase.restartgameusecase.RestartGameUseCase
import com.nerodev.optimaltic.domain.usecase.setdifficultyusecase.SetDifficultyUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModelImpl(
    private val makeMoveUseCase: MakeMoveUseCase,
    private val getBotMoveUseCase: GetBotMoveUseCase,
    private val checkWinnerUseCase: CheckWinnerUseCase,
    private val restartGameUseCase: RestartGameUseCase,
    private val setDifficultyUseCase: SetDifficultyUseCase
) : ViewModel(), MainViewModel {

    private val _state = MutableLiveData(GameState())
    override val state: LiveData<GameState> = _state

    private val _oWins = MutableLiveData(0)
    override val oWins: LiveData<Int> = _oWins

    private val _xWins = MutableLiveData(0)
    override val xWins: LiveData<Int> = _xWins

    private val _draws = MutableLiveData(0)
    override val draws: LiveData<Int> = _draws

    private val _difficulty = MutableLiveData(Difficulty.Normal)
    override val difficulty: LiveData<Difficulty> = _difficulty

    override fun makeMove(index: Int) {
        viewModelScope.launch {
            val currentState = _state.value ?: return@launch

            val newState = makeMoveUseCase(index, currentState)

            val winner = checkWinnerUseCase(newState.board)

            _state.value = newState.copy(
                winner = winner,
                isGameOver = winner != null || newState.board.none { it == Player.NONE }
            )

            if (_state.value?.isGameOver == true) {
                when (winner) {
                    Player.O -> _oWins.value = (_oWins.value ?: 0) + 1
                    Player.X -> _xWins.value = (_xWins.value ?: 0) + 1
                    else -> _draws.value = (_draws.value ?: 0) + 1
                }
            }

            if (_state.value?.currentPlayer == Player.O &&
                _state.value?.isGameOver == false &&
                _difficulty.value != Difficulty.Multiplayer
            ) {
                botMove()
            }
        }
    }

    private fun botMove() {
        viewModelScope.launch {
            delay(1000L)
            val currentState = _state.value ?: return@launch
            val bestMove = getBotMoveUseCase(currentState.board, _difficulty.value ?: Difficulty.Normal)

            if (bestMove != -1) {
                makeMove(bestMove)
            }
        }
    }

    override fun restartGame() {
        _state.value = restartGameUseCase()
    }

    override fun resetGame() {
        _state.value = GameState()
        _oWins.value = 0
        _xWins.value = 0
        _draws.value = 0
    }

    override fun setDifficulty(newDifficulty: Difficulty) {
        _difficulty.value = newDifficulty
        setDifficultyUseCase(newDifficulty)
        resetGame()
    }
}