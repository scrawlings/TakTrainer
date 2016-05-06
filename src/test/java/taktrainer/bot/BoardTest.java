package taktrainer.bot;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {
    @Test
    public void testEmptyBoard() throws Exception {
        final Board board = new Board(6);
        assertThat(board.toTPS()).isEqualTo("[TPS \"x6/x6/x6/x6/x6/x6 1 1\"]");
    }

    @Test
    public void testSimpleMoves() throws Exception {
        final Board board = new Board(6);
        assertThat(board.toTPS()).isEqualTo("[TPS \"x6/x6/x6/x6/x6/x6 1 1\"]");
        board.applyMove(new Move(board.cell(2, 3), Board.F));
        assertThat(board.toTPS()).isEqualTo("[TPS \"x6/x6/x1,1,x4/x6/x6/x6 2 1\"]");
        board.applyMove(new Move(board.cell(1, 1), Board.F));
        assertThat(board.toTPS()).isEqualTo("[TPS \"2,x5/x6/x1,1,x4/x6/x6/x6 1 2\"]");
        board.applyMove(new Move(board.cell(3, 3), Board.F));
        assertThat(board.toTPS()).isEqualTo("[TPS \"2,x5/x6/x1,1,1,x3/x6/x6/x6 2 2\"]");
        board.applyMove(new Move(board.cell(4, 4), Board.S));
        assertThat(board.toTPS()).isEqualTo("[TPS \"2,x5/x6/x1,1,1,x3/x3,2S,x2/x6/x6 1 3\"]");
        board.applyMove(new Move(board.cell(6, 6), Board.C));
        assertThat(board.toTPS()).isEqualTo("[TPS \"2,x5/x6/x1,1,1,x3/x3,2S,x2/x6/x5,1C 2 3\"]");
    }

    @Test
    public void testBuildBoardStateFromTPSWithoutStacks() throws Exception {
        final Board board = new Board(6);
        board.loadTPS("[TPS \"2,x5/x6/x1,1,1,x3/x6/x6/x6 2 23\"]");
        assertThat(board.toTPS()).isEqualTo("[TPS \"2,x5/x6/x1,1,1,x3/x6/x6/x6 2 23\"]");
        board.applyMove(new Move(board.cell(4, 4), Board.S));
        assertThat(board.toTPS()).isEqualTo("[TPS \"2,x5/x6/x1,1,1,x3/x3,2S,x2/x6/x6 1 24\"]");
    }

    @Test
    public void testSimpleUnblockedSlidesOnEmptyCells() throws Exception {
    }

    @Test
    public void testBuildBoardStateFromTPSWithStacks() throws Exception {
    }

    @Test
    public void testSimpleUnblockedSlidesOnCellsWithStacks() throws Exception {
    }

    @Test
    public void testSlidesBlockedByEdges() throws Exception {
    }

    @Test
    public void testSlidesBlockedByWallsAndCapstones() throws Exception {
    }

    @Test
    public void testSlidesWhereCapstonesToppleStandingStones() throws Exception {
    }
}
