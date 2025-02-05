package com.nerodev.optimaltic.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.nerodev.optimaltic.data.model.Player

@Composable
fun Board(
    board: List<Player>,
    isPlayerTurn: Boolean,
    onCellClick: (Int) -> Unit
) {
    Column {
        for (row in 0 until 3) {
            Row {
                for (col in 0 until 3) {
                    val index = row * 3 + col
                    Cell(
                        player = board[index],
                        isClickable = isPlayerTurn && board[index] == Player.NONE,
                        onClick = { onCellClick(index) }
                    )
                }
            }
        }
    }
}