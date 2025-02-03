package com.nerodev.optimaltic.domain.usecase.restartgameusecase

import com.nerodev.optimaltic.domain.model.GameState
import com.nerodev.optimaltic.domain.repository.GameRepository

class RestartGameUseCase(private val gameRepository: GameRepository) {
    operator fun invoke(): GameState {
        return gameRepository.restartGame()
    }
}