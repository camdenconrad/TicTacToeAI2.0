import java.awt.*;

public class Board implements Substate {

    private static final char OPEN_SPACE = 'W';
    char[][] board = new char[3][3];
    int moves = 0;

    public Board() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = OPEN_SPACE;
            }

        }
    }

    public Board(Board b) {
        for (int i = 0; i < b.board.length; i++) {
            System.arraycopy(b.board[i], 0, this.board[i], 0, b.board[i].length);
        }
        this.moves = b.moves;
    }

    public static Point extractMove(Board state, Board state2) {
        if (state != null && state2 != null) {
            if (state.isSubstate(state2)) {
                for (int i = 0; i < state.board.length; i++) {
                    for (int j = 0; j < state.board[i].length; j++) {
                        if (state.board[i][j] != state2.board[i][j]) {
                            return new Point(i, j);
                        }
                    }
                }
            }
        }
        return null;
    }

    public Board clone() {
        return new Board(this);
    }

    public boolean doMove(int x, int y) {
        if (board[x][y] == OPEN_SPACE) {
            board[x][y] = getTurn();
            moves++;
            return true;
        }
        return false;
    }

    private char getTurn() {
        if (moves % 2 == 0) {
            return 'x';
        }
        return 'o';
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                sb.append(board[i][j]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public boolean isSubstate(Board state) {
        if (this.moves == state.moves - 1) {
            int numDiff = 0;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (state.board[i][j] != this.board[i][j]) {
                        if (state.board[i][j] == OPEN_SPACE || this.board[i][j] == OPEN_SPACE) {
                            numDiff++;
                        }
                    }
                }
            }
            return numDiff == 1;
        }
        return false;
    }

    public boolean winState() {
        for (int row = 0; row < board.length; row++) {
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                if (board[row][0] != OPEN_SPACE) {
                    return true;
                }
            }
        }

        // Check columns
        for (int col = 0; col < board[0].length; col++) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                if (board[0][col] != OPEN_SPACE) {
                    return true;
                }
            }
        }

        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] != OPEN_SPACE) {
                return true;
            }
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2] != OPEN_SPACE;
        }

        // No line with all the same letters found
        return false;
    }

    public boolean winState(char WINNER) {
        for (int row = 0; row < board.length; row++) {
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                if (board[row][0] == WINNER) {
                    return true;
                }
            }
        }

        // Check columns
        for (int col = 0; col < board[0].length; col++) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                if (board[0][col] == WINNER) {
                    return true;
                }
            }
        }

        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == WINNER) {
                return true;
            }
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2] == WINNER;
        }

        // No line with all the same letters found
        return false;
    }

    public Point getCartesian() {
        int x = (int) (Math.random() * 0);
        int y = (int) (Math.random() * 0);
        int spacingFactor = 4000; // Adjust this value to increase or decrease node spacing

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != OPEN_SPACE) {
                    x += (i - 1) * spacingFactor;
                    y += (j - 1) * spacingFactor;
                }
            }
        }

        return new Point(x / (1 + 12 * moves), y / (1 + 12 * moves));
    }

    public int countOccurrences(char z) {
        int occ = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == z) {
                    occ++;
                }
            }

        }

        return occ;
    }

    public boolean equals(Board board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.board[i][j] != this.board[i][j]) {
                    return false;
                }
            }

        }
        return true;
    }

    public void reset() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = OPEN_SPACE;
            }
        }
        moves = 0;
    }

}
