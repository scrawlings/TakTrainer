package taktrainer.bot;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static taktrainer.bot.Board.NoPlayer;
import static taktrainer.bot.Board.P1;
import static taktrainer.bot.Board.P2;
import static taktrainer.bot.TPS.loadTPS;
import static taktrainer.bot.TPS.toTPS;
import static taktrainer.bot.Victory.flatsMajority;

public class VictoryTest {
    @Test
    public void testFlatsP1Win() throws Exception {
        final Board board = new Board(6);
        loadTPS(board, "[TPS \"x6/x6/1,x5/2221,x5/2,x5/1,x5 1 24\"]");
        assertThat(flatsMajority(board)).isEqualTo(P1);
    }

    @Test
    public void testFlatsTie() throws Exception {
        final Board board = new Board(6);
        loadTPS(board, "[TPS \"x6/x6/1,x5/2221,x5/2,x5/1,2,2,x3 1 24\"]");
        assertThat(flatsMajority(board)).isEqualTo(NoPlayer);
    }

    @Test
    public void testFlatsP2WinWithCap() throws Exception {
        final Board board = new Board(6);
        loadTPS(board, "[TPS \"x5,2C/x6/121S,x5/2221,x5/2,x5/1,2,2,x3 1 24\"]");
        assertThat(flatsMajority(board)).isEqualTo(P2);
    }


    @Test
    public void testFlatsP1TieBecauseStandingStonesDoNotCount() throws Exception {
        final Board board = new Board(6);
        loadTPS(board, "[TPS \"x6/x6/1,x5/2221S,x5/2,x5/1S,x5 1 24\"]");
        assertThat(flatsMajority(board)).isEqualTo(NoPlayer);
    }

    @Test
    public void testFlatsP1WinBecauseStandingStonesDoNotCount() throws Exception {
        final Board board = new Board(6);
        loadTPS(board, "[TPS \"x6/x6/1,x5/2221,x5/2,x5/1,2S,2,x3 1 24\"]");
        assertThat(flatsMajority(board)).isEqualTo(P1);
    }
}