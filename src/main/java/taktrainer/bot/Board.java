package taktrainer.bot;

public class Board {
    final Cell board[];
    final int size;
    final int squares;
    int turn;
    int move;

    public static final int offRight = 1;
    public static final int offLeft = -1;
    public int offUp;
    public int offDown;

    public static final Cell EMPTY = null;

    public static final int NoPlayer = 0;
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
        offUp = -size;
        offDown = size;
    }

//    public Board duplicateBoard() {
//        final Board dup = new Board(size);
//
//        dup.turn = this.turn;
//        dup.move = this.move;
//        System.arraycopy(this.board, 0, dup.board, 0, dup.squares);
//
//        return dup;
//    }

    public void applyMove(Place p) {
        Cell cell = new Cell(turn + p.piece, board[p.at]);
        board[p.at] = cell;
        updateTurnAndMove();
    }

    public void applyMove(Slide s) {
        int destination = (s.offset * s.partition.length) + s.from;

        for (int a = s.partition.length - 1; a >= 0; a--) {
            Cell moving = board[s.from];
            Cell bottom = moving;
            for (int b = 0; b < s.partition[a]; b++) {
                bottom = board[s.from];
                board[s.from] = (bottom == null) ? null : bottom.rest;
            }
            bottom.rest = board[destination];
            board[destination] = moving;
            destination -= s.offset;
        }
        updateTurnAndMove();
    }

    private void updateTurnAndMove() {
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
