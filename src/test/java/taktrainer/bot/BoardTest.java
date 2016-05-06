package taktrainer.bot;

import org.junit.Ignore;
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
        board.applyMove(new Place(board.cell(2, 3), Board.F));
        assertThat(board.toTPS()).isEqualTo("[TPS \"x6/x6/x1,1,x4/x6/x6/x6 2 1\"]");
        board.applyMove(new Place(board.cell(1, 1), Board.F));
        assertThat(board.toTPS()).isEqualTo("[TPS \"2,x5/x6/x1,1,x4/x6/x6/x6 1 2\"]");
        board.applyMove(new Place(board.cell(3, 3), Board.F));
        assertThat(board.toTPS()).isEqualTo("[TPS \"2,x5/x6/x1,1,1,x3/x6/x6/x6 2 2\"]");
        board.applyMove(new Place(board.cell(4, 4), Board.S));
        assertThat(board.toTPS()).isEqualTo("[TPS \"2,x5/x6/x1,1,1,x3/x3,2S,x2/x6/x6 1 3\"]");
        board.applyMove(new Place(board.cell(6, 6), Board.C));
        assertThat(board.toTPS()).isEqualTo("[TPS \"2,x5/x6/x1,1,1,x3/x3,2S,x2/x6/x5,1C 2 3\"]");
    }

    @Test
    public void testBuildBoardStateFromTPSWithoutStacks() throws Exception {
        final Board board = new Board(6);
        board.loadTPS("[TPS \"2,x5/x6/x1,1,1,x3/x6/x6/x6 2 23\"]");
        assertThat(board.toTPS()).isEqualTo("[TPS \"2,x5/x6/x1,1,1,x3/x6/x6/x6 2 23\"]");
        board.applyMove(new Place(board.cell(4, 4), Board.S));
        assertThat(board.toTPS()).isEqualTo("[TPS \"2,x5/x6/x1,1,1,x3/x3,2S,x2/x6/x6 1 24\"]");
    }

    @Test
    public void testSimpleUnblockedSlidesOnEmptyCells() throws Exception {
        final Board board = new Board(6);
        board.loadTPS("[TPS \"x6/x6/x1,1,1,x3/x6/x6/x6 2 23\"]");
        final int[] partition = {1};
        board.applyMove(new Slide(board.cell(3, 3), board.offRight, partition));
        assertThat(board.toTPS()).isEqualTo("[TPS \"x6/x6/x1,1,x1,1,x2/x6/x6/x6 1 24\"]");
    }

    @Test
    public void testBuildBoardStateFromTPSWithStacks() throws Exception {
        final Board board = new Board(6);
        board.loadTPS("[TPS \"x6/x6/x2,1212,x3/x6/x6/x6 2 23\"]");
        assertThat(board.toTPS()).isEqualTo("[TPS \"x6/x6/x2,1212,x3/x6/x6/x6 2 23\"]");
    }

    @Test
    public void testSimpleUnblockedSlidesOnCellsWithStacks() throws Exception {
        final Board board = new Board(6);
        board.loadTPS("[TPS \"x6/x6/x6/22,x5/x6/1221121S,x5 2 23\"]");
        final int[] partition = {1,2,3};
        board.applyMove(new Slide(board.cell(1, 6), board.offUp, partition));
        assertThat(board.toTPS()).isEqualTo("[TPS \"x6/x6/1S,x5/2212,x5/221,x5/1,x5 1 24\"]");
    }

    @Ignore
    @Test
    public void testSlidesBlockedByEdges() throws Exception {
    }

    @Ignore
    @Test
    public void testSlidesBlockedByWallsAndCapstones() throws Exception {
    }

    @Ignore
    @Test
    public void testSlidesWhereCapstonesToppleStandingStones() throws Exception {
    }
}
