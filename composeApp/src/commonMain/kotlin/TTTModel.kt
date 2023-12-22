enum class Player {
    X, O
}

class TTTGame {
    var board: Array<Array<Player?>> = Array(3) { Array(3) { null } }
    var currentPlayer: Player = Player.X
        private set

    fun makeMove(row: Int, col: Int): Boolean {
        if (board[row][col] != null) {
            return false
        }
        board = board.copyOf().also {
            it[row][col] = currentPlayer
        }
        currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
        return true
    }

    fun checkWin(): Player? {
        // Проверка горизонтали и вертикали
        for (i in 0..2) {
            if (board[i][0] != null && board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                return board[i][0]
            }
            if (board[0][i] != null && board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
                return board[0][i]
            }
        }
        // Проверка диагонали
        if (board[0][0] != null && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            return board[0][0]
        }
        if (board[0][2] != null && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            return board[0][2]
        }
        // Никто не выиграл
        return null
    }

    fun checkDrow(): Boolean {
        return board.all {
            it.all {
                it != null
            }
        }
    }
}