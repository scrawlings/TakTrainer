package taktrainer.bot;

import java.text.ParseException;

public class Board {
    final Cell board[];
    final int size;
    final int squares;
    int turn;
    int move;

    public static final Cell EMPTY = null;

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
        board = new Cell[squares];
        this.size = size;
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
                    sb.append(board[cell].cell / 10);
                    if (board[cell].cell % 10 == S) {
                        sb.append("S");
                    }
                    if (board[cell].cell % 10 == C) {
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
        Cell cell = new Cell(turn + m.piece, board[m.at]);
        board[m.at] = cell;
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

    public void loadTPS(String tps) throws ParseException {
        String prefix = tps.substring(0, 6);

        if (!prefix.equals("[TPS \"")) {
            throw new ParseException("Invalid TPS prefix", 0);
        }

        int cell = 0;
        boolean processingCells = true;
        int a = 6;
        while (processingCells) {
            switch (tps.charAt(a)) {
                case 'x':
                    a++;
                    int run = Integer.parseInt(String.valueOf(tps.charAt(a)));
                    cell += run;
                    break;

                case '2':
                case '1':
                    int piece = (tps.charAt(a) == '1') ? P1 : P2;
                    switch (tps.charAt(a+1)) {
                        case 'S':
                            piece += S;
                            a++;
                            break;
                        case 'C':
                            piece += C;
                            a++;
                            break;
                        default:
                            piece += F;
                    };

                    Cell c = new Cell(piece, board[cell]);
                    board[cell] = c;
                    cell++;
                    break;

                case ' ':
                    processingCells = false;
                    break;
            }
            a++;
        }

        boolean processingTurn = true;
        while (processingTurn) {
            if (tps.charAt(a) != ' ') {
                turn = 10 * Integer.parseInt(String.valueOf(tps.charAt(a)));
                processingTurn = false;
            }
            a++;
        }


        boolean processingMove = true;
        move = 0;
        while (processingMove) {
            if (tps.charAt(a) != ' ') {
                move = (move * 10) + Integer.parseInt(String.valueOf(tps.charAt(a)));
                if (!Character.isDigit(tps.charAt(a+1))) {
                    processingMove = false;
                }
            }
            a++;
        }
    }
}
