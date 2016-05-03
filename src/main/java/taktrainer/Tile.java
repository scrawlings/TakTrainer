package taktrainer;

public class Tile {
    private TileType type;
    final Player player;


    public enum TileType {unplayed, flat, wall, cap};
    public enum Player {A, B};

    private Tile(TileType type, Player player) {
        this.type = type;
        this.player = player;
    }

    public static Tile unplayedTile(Player player) {
        return new Tile(TileType.unplayed, player);
    }

    public static Tile capTile(Player player) {
        return new Tile(TileType.cap, player);
    }

    public static Tile as(TileType type, Tile tile) {
        tile.type = type;
        return tile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;

        if (type != tile.type) return false;
        return player == tile.player;

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (player != null ? player.hashCode() : 0);
        return result;
    }
}
