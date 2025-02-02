package com.nerodev.optimaltic.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nerodev.optimaltic.data.Difficulty
import com.nerodev.optimaltic.data.GameState
import com.nerodev.optimaltic.data.Player
import com.nerodev.optimaltic.presentation.component.Board
import com.nerodev.optimaltic.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val gameState = viewModel.state.observeAsState(initial = GameState()).value
    val oWins = viewModel.oWins.observeAsState(initial = 0).value
    val xWins = viewModel.xWins.observeAsState(initial = 0).value
    val draws = viewModel.draws.observeAsState(initial = 0).value
    val difficulty = viewModel.difficulty.observeAsState(initial = Difficulty.Normal).value

    var showConfirmDialog = remember { mutableStateOf(false) }
    var pendingDifficultyChange = remember { mutableStateOf<Difficulty?>(null) }
    val textColor = MaterialTheme.colorScheme.onBackground
    val fadedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "OptimalTic") }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .systemBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (gameState.isGameOver) {
                    Text(
                        text = if (gameState.winner != null) {
                            "${gameState.winner} Win!!"
                        } else {
                            "Draw!!"
                        },
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(12.dp)
                    )
                } else {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "X",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (gameState.currentPlayer == Player.X) textColor else fadedTextColor
                        )
                        Text(
                            text = "vs",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                        Text(
                            text = "O",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (gameState.currentPlayer == Player.O) textColor else fadedTextColor
                        )
                    }
                }

                Text(
                    text = "Game Mode: $difficulty",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text("X Wins: $xWins | O Wins: $oWins | Draws: $draws", fontSize = 18.sp)

                Spacer(modifier = Modifier.height(12.dp))

                Board(
                    board = gameState.board,
                    isPlayerTurn = gameState.currentPlayer == Player.X,
                    onCellClick = { index -> viewModel.makeMove(index) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Button(
                        onClick = { viewModel.restartGame() },
                        colors = ButtonColors(
                            containerColor = Color.Gray,
                            contentColor = Color.Unspecified,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.Unspecified
                        )
                    ) {
                        Text(text = if(gameState.isGameOver) "Play Again?" else "Restart Game", modifier = Modifier.padding(horizontal = 6.dp))
                    }
                    Spacer(Modifier.width(4.dp))

                    Button(
                        onClick = {
                            val currentIndex = Difficulty.entries.indexOf(difficulty)
                            val nextDifficulty = Difficulty.entries[(currentIndex + 1) % Difficulty.entries.size]

                            if (nextDifficulty == Difficulty.Impossible) {
                                pendingDifficultyChange.value = nextDifficulty
                                showConfirmDialog.value = true
                            } else {
                                viewModel.setDifficulty(nextDifficulty)
                            }
                        },
                        colors = ButtonColors(
                            containerColor = Color.Gray,
                            contentColor = Color.Unspecified,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.Unspecified
                        )
                    ) {
                        Text("Mode: $difficulty")
                    }
                }
            }

            if (showConfirmDialog.value) {
                AlertDialog(
                    onDismissRequest = { showConfirmDialog.value = false },
                    title = { Text("Are you sure?") },
                    text = { Text("Change mode to Impossible?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                pendingDifficultyChange.value?.let {
                                    viewModel.setDifficulty(it)
                                }
                                showConfirmDialog.value = false
                            }
                        ) {
                            Text("Yes")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showConfirmDialog.value = false }
                        ) {
                            Text("No")
                        }
                    }
                )
            }
        }
    }
}