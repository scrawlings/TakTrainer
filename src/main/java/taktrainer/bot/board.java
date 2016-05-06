package taktrainer.bot;

public class Board {
    final int board[];
    final int size;
    final int squares;
    int turn;
    int move;

    public static final int EMPTY = 0;

    public static final int P1 = 10;
    public static final int P2 = 20;

    public static final int F = 1;
    public static final int S = 2;
    public static final int C = 3;

    public static final int P1F = P1 + F;
    public static final int P1S = P1 + S;
    public static final int P1C = P1 + C;
    public static final int P2F = P2 + F;
    public static final int P2S = P2 + S;
    public static final int P2C = P2 + C;

    public Board(final int size) {
        turn = P1;
        move = 1;
        squares = size * size;
        board = new int[squares];
        this.size = size;
        for (int a = 0; a < squares; a++) {
            board[a] = EMPTY;
        }
    }

    public String toTPS() {
        StringBuilder sb = new StringBuilder();
        sb.append("[TPS \"");

        for (int row = 0; row < size; row++) {
            int emptyCount = 0;
            boolean first = true;
            for (int cell = row * size; cell < (row + 1) * size; cell++) {
                if (board[cell] == EMPTY) {
                    emptyCount++;
                }
                else {
                    if (!first) {
                        sb.append(",");
                    }
                    if (emptyCount > 0) {
                        sb.append("x").append(emptyCount).append(",");
                    }
                    sb.append(board[cell] / 10);
                    if (board[cell] % 10 == S) {
                        sb.append("S");
                    }
                    if (board[cell] % 10 == C) {
                        sb.append("C");
                    }
                    emptyCount = 0;
                    first = false;
                }
            }
            if (emptyCount > 0) {
                if (!first) {
                    sb.append(",");
                }
                sb.append("x").append(emptyCount);
            }
            if (row + 1 < size) {
                sb.append("/");
            }
        }

        sb.append(" ").append(turn / 10);
        sb.append(" ").append(move);
        sb.append("\"]");
        return sb.toString();
    }

    public Board duplicateBoard() {
        final Board dup = new Board(size);

        dup.turn = this.turn;
        dup.move = this.move;
        System.arraycopy(this.board, 0, dup.board, 0, dup.squares);

        return dup;
    }

    public void applyMove(Move m) {
        board[m.at] = turn + m.piece;
        if (turn == P1) {
            turn = P2;
        }
        else {
            move++;
            turn = P1;
        }
    }

    public int cell(final int x, final int y) {
        return (x - 1) + ((y - 1) * size);
    }
}
