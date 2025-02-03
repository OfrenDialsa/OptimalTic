package com.nerodev.optimaltic.core.module

import com.nerodev.optimaltic.data.repository.GameRepositoryImpl
import com.nerodev.optimaltic.domain.repository.GameRepository
import com.nerodev.optimaltic.domain.usecase.botmoveusecase.GetBotMoveUseCase
import com.nerodev.optimaltic.domain.usecase.checkwinnerusecase.CheckWinnerUseCase
import com.nerodev.optimaltic.domain.usecase.makemoveusecase.MakeMoveUseCase
import com.nerodev.optimaltic.domain.usecase.restartgameusecase.RestartGameUseCase
import com.nerodev.optimaltic.domain.usecase.setdifficultyusecase.SetDifficultyUseCase
import com.nerodev.optimaltic.presentation.viewmodel.MainViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Provide GameRepository implementation
    single<GameRepository> { GameRepositoryImpl() }

    // Provide Use Cases
    factory { MakeMoveUseCase(get()) }
    factory { GetBotMoveUseCase(get()) }
    factory { CheckWinnerUseCase(get()) }
    factory { RestartGameUseCase(get()) }
    factory { SetDifficultyUseCase(get()) }

    // Provide ViewModel
    viewModel { MainViewModelImpl(get(), get(), get(), get(), get()) }
}