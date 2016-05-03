package taktrainer;

import taktrainer.Tile.TileType;

import java.util.LinkedList;
import java.util.List;

import static taktrainer.Tile.Player.A;
import static taktrainer.Tile.Player.B;
import static taktrainer.Tile.TileType.cap;
import static taktrainer.Unplayed.UnplayedWithCap;

public class Game {
    final Board board;
    final Unplayed playerA;
    final Unplayed playerB;

    public Game(Board board, Unplayed playerA, Unplayed playerB) {
        this.board = board;
        this.playerA = playerA;
        this.playerB = playerB;
    }

    public static Game newGame5x5() {
        Board board = new Board(5);
        Unplayed playerA = UnplayedWithCap(20, A);
        Unplayed playerB = UnplayedWithCap(20, B);

        return new Game(board, playerA, playerB);
    }

    public List<Move> possibleMovesForPlayerA() {
        return possibleMovesForPlayer(playerA);
    }

    public List<Move> possibleMovesForPlayerB() {
        return possibleMovesForPlayer(playerB);
    }

    public List<Move> possibleMovesForPlayer(Unplayed unplayed) {
        final List<Move> moves = new LinkedList<>();

        moves.addAll(placeCapMoves(unplayed, board));
        moves.addAll(placeFlatMoves(unplayed, board));
        moves.addAll(placeWallMoves(unplayed, board));

        moves.addAll(pileMoves(unplayed, board));

        return moves;
    }

    private List<Move> pileMoves(Unplayed unplayed, Board board) {
        final List<Move> moves = new LinkedList<>();

        for (At at : board.controlledLocations(unplayed.player)) {
            Pile pile = board.pile(at);
            final int maxStack = Math.min(board.size, pile.size());
            for (int a = 1; a <= maxStack; a++) {
                List<List<Pile>> partitions = pile.partitions(a);
                for (List<Pile> partition : partitions) {
                    moves.addAll(board.validMovesForPartitionFrom(board, at, a, partition));
                }
            }
        }

        return moves;
    }

    private List<MoveUnplayed> placeFlatMoves(Unplayed unplayed, Board board) {
        return placeNormalMoves(unplayed, board, TileType.flat);
    }

    private List<MoveUnplayed> placeWallMoves(Unplayed unplayed, Board board) {
        return placeNormalMoves(unplayed, board, TileType.wall);
    }

    private List<MoveUnplayed> placeNormalMoves(Unplayed unplayed, Board board, TileType tileType) {
        final List<MoveUnplayed> moves = new LinkedList<MoveUnplayed>();

        if (unplayed.hasUnplayedNormalTiles()) {
            for (At at : board.emptyLocations()) {
                moves.add(new MoveUnplayed(
                        unplayed.peekNormalTile(),
                        tileType,
                        new FromUnplayed(unplayed),
                        at
                ));
            }
        }

        return moves;
    }

    private List<MoveUnplayed> placeCapMoves(Unplayed unplayed, Board board) {
        final List<MoveUnplayed> moves = new LinkedList<MoveUnplayed>();

        if (unplayed.hasUnplayedCap()) {
            for (At at : board.emptyLocations()) {
                moves.add(new MoveUnplayed(
                        unplayed.peekCap(),
                        cap,
                        new FromUnplayed(unplayed),
                        at
                ));
            }
        }

        return moves;
    }
}
