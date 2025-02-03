package com.nerodev.optimaltic.domain.usecase.makemoveusecase

import com.nerodev.optimaltic.domain.model.GameState
import com.nerodev.optimaltic.domain.repository.GameRepository

class MakeMoveUseCase(private val gameRepository: GameRepository) {
    suspend operator fun invoke(index: Int, gameState: GameState): GameState {
        return gameRepository.makeMove(index, gameState)
    }
}