package taktrainer;

import taktrainer.Tile.Player;

import java.util.LinkedList;

public class Unplayed {
    private final LinkedList<Tile> tiles = new LinkedList<Tile>();
    final Player player;
    final Tile cap;

    private Unplayed(int normal, Player player, Tile cap) {
        this.player = player;
        this.cap = cap;

        for (int a = 0; a < normal; a++) {
            tiles.add(Tile.unplayedTile(player));
        }
    }

    public static Unplayed UnplayedWithCap(int normal, Player player) {
        return new Unplayed(normal, player, Tile.capTile(player));
    }

    public static Unplayed UnplayedWithoutCap(int normal, Player player) {
        return new Unplayed(normal, player, null);
    }

    public Tile peekNormalTile() {
        return tiles.peekFirst();
    }

    public boolean hasUnplayedNormalTiles() {
        return !tiles.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unplayed unplayed = (Unplayed) o;

        if (tiles != null ? !tiles.equals(unplayed.tiles) : unplayed.tiles != null) return false;
        return cap != null ? cap.equals(unplayed.cap) : unplayed.cap == null;

    }

    @Override
    public int hashCode() {
        int result = tiles != null ? tiles.hashCode() : 0;
        result = 31 * result + (cap != null ? cap.hashCode() : 0);
        return result;
    }

    public boolean hasUnplayedCap() {
        return cap != null;
    }

    public Tile peekCap() {
        return cap;
    }
}
