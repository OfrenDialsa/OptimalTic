package com.nerodev.optimaltic.data

enum class Player { X, O, NONE }
enum class Difficulty { Standard, Impossible }

data class GameState(
    val board: List<Player> = List(9) { Player.NONE },
    val currentPlayer: Player = Player.X,
    val isGameOver: Boolean = false,
    val winner: Player? = null,
    val winPattern: List<Int>? = null
)