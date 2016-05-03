package taktrainer.v001;

import org.junit.Test;
import taktrainer.v001.At;
import taktrainer.v001.Board;

import static org.assertj.core.api.Assertions.assertThat;
import static taktrainer.v001.Tile.Player.A;
import static taktrainer.v001.Tile.Player.B;
import static taktrainer.v001.Tile.TileType.flat;
import static taktrainer.v001.Tile.TileType.wall;
import static taktrainer.v001.Tile.as;
import static taktrainer.v001.Tile.unplayedTile;
import static taktrainer.v001.Tile.capTile;

public class BoardTest {
    @Test
    public void testEmptyBoardLocationsInitiallyAll() throws Exception {
        Board board = new Board(3);

        At at00 = new At(0, 0);
        At at01 = new At(0, 1);
        At at02 = new At(0, 2);
        At at10 = new At(1, 0);
        At at11 = new At(1, 1);
        At at12 = new At(1, 2);
        At at20 = new At(2, 0);
        At at21 = new At(2, 1);
        At at22 = new At(2, 2);

        assertThat(board.emptyLocations()).containsExactly(at00, at01, at02, at10, at11, at12, at20, at21, at22);
    }

    @Test
    public void testEmptyBoardLocationsExcludingThoseThatAreOccupied() throws Exception {
        Board board = new Board(3);

        At at01 = new At(0, 1);
        At at12 = new At(1, 2);
        At at20 = new At(2, 0);

        board.putTileOnTopAt(at01, as(flat, unplayedTile(A)));
        board.putTileOnTopAt(at12, as(wall, unplayedTile(B)));
        board.putTileOnTopAt(at20, capTile(A));

        At at00 = new At(0, 0);
        At at02 = new At(0, 2);
        At at10 = new At(1, 0);
        At at11 = new At(1, 1);
        At at21 = new At(2, 1);
        At at22 = new At(2, 2);

        assertThat(board.emptyLocations()).containsExactly(at00, at02, at10, at11, at21, at22);
    }

    @Test
    public void testReturnsCorrectlyOccupiedLocations() throws Exception {
        Board board = new Board(3);

        At at01 = new At(0, 1);
        At at02 = new At(0, 2);
        At at12 = new At(1, 2);
        At at20 = new At(2, 0);

        board.putTileOnTopAt(at01, as(flat, unplayedTile(A)));
        board.putTileOnTopAt(at02, as(flat, unplayedTile(B)));
        board.putTileOnTopAt(at12, as(wall, unplayedTile(B)));
        board.putTileOnTopAt(at20, capTile(A));

        assertThat(board.controlledLocations(B)).containsExactlyInAnyOrder(at02, at12);
    }
}