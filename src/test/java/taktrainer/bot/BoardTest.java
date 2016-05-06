package taktrainer.bot;

import org.junit.Test;

public class BoardTest {
    @Test
    public void testEmptyBoard() throws Exception {
        final Board board = new Board(6);
        org.assertj.core.api.Assertions.assertThat(board.toTPS()).isEqualTo("[TPS \"x6/x6/x6/x6/x6/x6 1 1\"]");
    }

    @Test
    public void testSimpleMoves() throws Exception {
        final Board board = new Board(6);
        org.assertj.core.api.Assertions.assertThat(board.toTPS()).isEqualTo("[TPS \"x6/x6/x6/x6/x6/x6 1 1\"]");
        board.applyMove(new Move(board.cell(2, 3), Board.F));
        org.assertj.core.api.Assertions.assertThat(board.toTPS()).isEqualTo("[TPS \"x6/x6/x1,1,x4/x6/x6/x6 2 1\"]");
        board.applyMove(new Move(board.cell(1, 1), Board.F));
        org.assertj.core.api.Assertions.assertThat(board.toTPS()).isEqualTo("[TPS \"2,x5/x6/x1,1,x4/x6/x6/x6 1 2\"]");
        board.applyMove(new Move(board.cell(3, 3), Board.F));
        org.assertj.core.api.Assertions.assertThat(board.toTPS()).isEqualTo("[TPS \"2,x5/x6/x1,1,1,x3/x6/x6/x6 2 2\"]");
        board.applyMove(new Move(board.cell(4, 4), Board.S));
        org.assertj.core.api.Assertions.assertThat(board.toTPS()).isEqualTo("[TPS \"2,x5/x6/x1,1,1,x3/x3,2S,x2/x6/x6 1 3\"]");
        board.applyMove(new Move(board.cell(6, 6), Board.C));
        org.assertj.core.api.Assertions.assertThat(board.toTPS()).isEqualTo("[TPS \"2,x5/x6/x1,1,1,x3/x3,2S,x2/x6/x5,1C 2 3\"]");
    }
}
