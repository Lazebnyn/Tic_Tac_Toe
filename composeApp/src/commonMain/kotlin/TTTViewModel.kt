
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed class TTTGameState{
    data object Game: TTTGameState()
    data object Draw: TTTGameState()
    data class Win(val player: Player): TTTGameState()
}

data class TTTState(
        val board: Array<Array<Player?>>,
        val gameState: TTTGameState
        )

class TTTViewModel {
    private val game = TTTGame()

    private val _board = MutableStateFlow(TTTState(board = game.board, gameState = TTTGameState.Game))
    val board = _board.asStateFlow()

    fun makeMove(row: Int, col: Int): Boolean {
        val moveMade = game.makeMove(row, col)
        _board.update {
            val winner = game.checkWin()
            it.copy(board = game.board, gameState = when{
                winner != null -> TTTGameState.Win(winner)
                game.checkDrow() -> TTTGameState.Draw
                else -> TTTGameState.Game
            })
        }
        return moveMade
    }

}