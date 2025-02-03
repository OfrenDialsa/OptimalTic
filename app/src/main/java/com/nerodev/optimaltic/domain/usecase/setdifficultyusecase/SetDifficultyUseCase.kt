package com.nerodev.optimaltic.domain.usecase.setdifficultyusecase

import com.nerodev.optimaltic.domain.model.Difficulty
import com.nerodev.optimaltic.domain.repository.GameRepository

class SetDifficultyUseCase(private val gameRepository: GameRepository) {
    operator fun invoke(difficulty: Difficulty) {
        gameRepository.setDifficulty(difficulty)
    }
}