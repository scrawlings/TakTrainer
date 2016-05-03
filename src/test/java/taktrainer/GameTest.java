package taktrainer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static taktrainer.Tile.Player.A;
import static taktrainer.Tile.Player.B;
import static taktrainer.Tile.TileType.flat;
import static taktrainer.Tile.TileType.wall;
import static taktrainer.Tile.TileType.cap;
import static taktrainer.Unplayed.UnplayedWithoutCap;
import static taktrainer.Unplayed.UnplayedWithCap;

public class GameTest {
    @Test
    public void testOpeningMovesWithoutCap() throws Exception {
        Unplayed unplayedA = UnplayedWithoutCap(1, A);
        Unplayed unplayedB = UnplayedWithoutCap(1, B);

        Game game = new Game(new Board(2), unplayedA, unplayedB);

        FromUnplayed from = new FromUnplayed(unplayedA);
        Tile tile = unplayedA.peekNormalTile();
        At at00 = new At(0, 0);
        At at01 = new At(0, 1);
        At at10 = new At(1, 0);
        At at11 = new At(1, 1);
        MoveUnplayed mf00 = new MoveUnplayed(tile, flat, from, at00);
        MoveUnplayed mf01 = new MoveUnplayed(tile, flat, from, at01);
        MoveUnplayed mf10 = new MoveUnplayed(tile, flat, from, at10);
        MoveUnplayed mf11 = new MoveUnplayed(tile, flat, from, at11);
        MoveUnplayed mw00 = new MoveUnplayed(tile, wall, from, at00);
        MoveUnplayed mw01 = new MoveUnplayed(tile, wall, from, at01);
        MoveUnplayed mw10 = new MoveUnplayed(tile, wall, from, at10);
        MoveUnplayed mw11 = new MoveUnplayed(tile, wall, from, at11);

        assertThat(game.possibleMovesForPlayerA()).containsExactlyInAnyOrder(mf00, mf10, mf01, mf11,
                mw00, mw10, mw01, mw11);
    }

    @Test
    public void testOpeningMovesWithCap() throws Exception {
        Unplayed unplayedA = UnplayedWithCap(1, A);
        Unplayed unplayedB = UnplayedWithCap(1, B);

        Game game = new Game(new Board(2), unplayedA, unplayedB);

        FromUnplayed from = new FromUnplayed(unplayedA);
        Tile tile = unplayedA.peekNormalTile();
        At at00 = new At(0, 0);
        At at01 = new At(0, 1);
        At at10 = new At(1, 0);
        At at11 = new At(1, 1);
        MoveUnplayed mf00 = new MoveUnplayed(tile, flat, from, at00);
        MoveUnplayed mf01 = new MoveUnplayed(tile, flat, from, at01);
        MoveUnplayed mf10 = new MoveUnplayed(tile, flat, from, at10);
        MoveUnplayed mf11 = new MoveUnplayed(tile, flat, from, at11);
        MoveUnplayed mw00 = new MoveUnplayed(tile, wall, from, at00);
        MoveUnplayed mw01 = new MoveUnplayed(tile, wall, from, at01);
        MoveUnplayed mw10 = new MoveUnplayed(tile, wall, from, at10);
        MoveUnplayed mw11 = new MoveUnplayed(tile, wall, from, at11);
        Tile capi = unplayedA.peekCap();
        MoveUnplayed mc00 = new MoveUnplayed(capi, cap, from, at00);
        MoveUnplayed mc01 = new MoveUnplayed(capi, cap, from, at01);
        MoveUnplayed mc10 = new MoveUnplayed(capi, cap, from, at10);
        MoveUnplayed mc11 = new MoveUnplayed(capi, cap, from, at11);

        assertThat(game.possibleMovesForPlayerA()).containsExactlyInAnyOrder(mf00, mf10, mf01, mf11,
                mw00, mw10, mw01, mw11,
                mc00, mc10, mc01, mc11);
    }
}