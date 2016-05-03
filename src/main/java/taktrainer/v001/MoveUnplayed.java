package taktrainer.v001;

import taktrainer.v001.Tile.TileType;

public class MoveUnplayed implements Move {
    final Tile tile;
    final TileType as;
    final From from;
    final At to;

    public MoveUnplayed(Tile tile, TileType as, From from, At to) {
        this.tile = tile;
        this.as = as;
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoveUnplayed move = (MoveUnplayed) o;

        if (tile != null ? !tile.equals(move.tile) : move.tile != null) return false;
        if (as != move.as) return false;
        if (from != null ? !from.equals(move.from) : move.from != null) return false;
        return to != null ? to.equals(move.to) : move.to == null;

    }

    @Override
    public int hashCode() {
        int result = tile != null ? tile.hashCode() : 0;
        result = 31 * result + (as != null ? as.hashCode() : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }
}
