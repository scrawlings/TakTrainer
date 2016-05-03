package taktrainer;

import taktrainer.Tile.Player;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Board {
    final int size;
    private final Pile[][] piles;

    public Board(int size) {
        this.size = size;

        piles = new Pile[size][];

        for (int i = 0; i < size; i++) {
             piles[i] =  new Pile[size];

            for (int j = 0; j < size; j++) {
                piles[i][j] = new Pile();
            }
        }
    }

    List<At> emptyLocations() {
        List<At> locations = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (piles[i][j].vacant()) {
                    locations.add(new At(i, j));
                }
            }
        }

        return locations;
    }

    public void putTileOnTopAt(At at, Tile tile) {
        piles[at.x][at.y].push(tile);
    }

    public List<At> controlledLocations(Player player) {
        List<At> locations = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (piles[i][j].controlled(player)) {
                    locations.add(new At(i, j));
                }
            }
        }

        return locations;
    }

    public Pile pile(At at) {
        return piles[at.x][at.y];
    }

    public List<Move> validMovesForPartitionFrom(Board board, At from, int size, List<Pile> partition) {
        List<Move> moves = new LinkedList<>();

        for (Direction direction : Direction.AllDirections()) {
            if (validate(board, from, direction, partition)) {
                moves.add(new MoveStack(
                        new FromPlayed(from, size)
                ));
            }
        }

        return moves;
    }

    private boolean validate(Board board, At from, Direction direction, List<Pile> partition) {
        return false;
    }
}
