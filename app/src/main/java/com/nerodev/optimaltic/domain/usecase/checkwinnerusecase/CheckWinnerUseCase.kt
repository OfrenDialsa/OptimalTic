package com.nerodev.optimaltic.domain.usecase.checkwinnerusecase

import com.nerodev.optimaltic.domain.model.Player
import com.nerodev.optimaltic.domain.repository.GameRepository

class CheckWinnerUseCase(private val gameRepository: GameRepository) {
    operator fun invoke(board: List<Player>): Player? {
        return gameRepository.checkWinner(board)
    }
}