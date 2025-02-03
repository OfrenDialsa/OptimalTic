package com.nerodev.optimaltic.domain.usecase.botmoveusecase

import com.nerodev.optimaltic.domain.model.Difficulty
import com.nerodev.optimaltic.domain.model.Player
import com.nerodev.optimaltic.domain.repository.GameRepository

class GetBotMoveUseCase(private val gameRepository: GameRepository) {
    suspend operator fun invoke(board: List<Player>, difficulty: Difficulty): Int {
        return gameRepository.getBotMove(board, difficulty)
    }
}