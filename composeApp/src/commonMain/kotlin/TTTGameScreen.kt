import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


@Composable
fun TTTGameScreen(viewModel: TTTViewModel) {
    val coroutineScope = rememberCoroutineScope()

    val state by viewModel.board.collectAsState()

    val gameState = state.gameState



    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        state.board.forEachIndexed { rowIndex: Int, row: Array<Player?> ->
            Row {
                row.forEachIndexed { colIndex, cell ->
                    Box(modifier = Modifier
                        .size(100.dp)
                        .border(2.dp, Color.Black)
                        .clickable {
                            coroutineScope.launch {
                                viewModel.makeMove(rowIndex, colIndex)
                            }
                        }) {
                        if (cell != null) {
                            Text(
                                text = cell.name,
                                fontSize = 30.sp,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
    AlertDialogWin(state.gameState)
}


@Composable
fun AlertDialogWin(
    gameState: TTTGameState
) {
    val alpha by animateFloatAsState(if (gameState is TTTGameState.Win) 0.2f else 0f)
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Green.copy(alpha = alpha)),
        contentAlignment = Alignment.Center
    ) {

        var winner by remember { mutableStateOf(Player.X) }
        if (gameState is TTTGameState.Win) {
            winner = gameState.player
        }
        AnimatedVisibility(
            gameState is TTTGameState.Win,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            Column(
                modifier = Modifier
                    .background(Color.Cyan, CircleShape)
                    .border(
                        BorderStroke(
                            2.dp,
                            Brush.linearGradient(listOf(Color.Magenta, Color.DarkGray, Color.Black))
                        ), CircleShape
                    )
                    .shadow(2.dp, CircleShape)
                    .padding(48.dp), horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = "Победили ${if (winner == Player.X) "крестики" else "нолики"}",
                    fontSize = 30.sp
                )
                Button(
                    onClick = {
                        // TODO
                    }, content = {
                        Text(
                            text = "Рестарт"
                        )
                    }
                )
            }
        }
    }
}
