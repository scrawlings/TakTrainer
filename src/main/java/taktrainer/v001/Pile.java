package taktrainer.v001;

import taktrainer.v001.Tile.Player;

import java.util.LinkedList;
import java.util.List;

public class Pile {
    private final LinkedList<Tile> tiles;

    public Pile() {
        this(new LinkedList<Tile>());
    }

    private Pile(LinkedList<Tile> tiles) {
        this.tiles = tiles;
    }

    public void push(Tile tile) {
        tiles.push(tile);
    }

    public boolean vacant() {
        return tiles.isEmpty();
    }

    public boolean controlled(Player player) {
        return !vacant() && tiles.peek().player == player;
    }

    public int size() {
        return tiles.size();
    }

    public Pile peekStack(int size) {
        LinkedList<Tile> topOfStack = new LinkedList<>();

        topOfStack.addAll(tiles.subList(0, size));

        return new Pile(topOfStack);
    }

    public List<List<Pile>> partitions(int n) {
        List<List<Pile>> partitionedPiles = new LinkedList<>();

        if (n > tiles.size()) {
            throw new IndexOutOfBoundsException();
        }

        int indexes[] = new int[n + 1];
        for (int a = 0 ; a < n; a++) {
            indexes[a] = a;
        }
        indexes[n] = tiles.size();

        partitionedPiles.add(partitionAccordingToIndex(indexes));

        while (roomToMove(indexes)) {
            nextPartition(indexes);
            partitionedPiles.add(partitionAccordingToIndex(indexes));
        };

        return partitionedPiles;
    }

    private void nextPartition(int[] indexes) {
        for (int a = indexes.length - 2; a > 0; a--) {
            if (indexes[a] < indexes[a+1] - 1) {
                indexes[a]++;
                int resetTo = indexes[a] + 1;
                for (int b = a+1; b < indexes.length - 1; b++) {
                    indexes[b] = resetTo++;
                }
                return;
            }
        }
    }

    private List<Pile> partitionAccordingToIndex(int[] indexes) {
        LinkedList<Pile> partitionedPile = new LinkedList<>();

        for (int a = 0; a < indexes.length - 1; a++) {
            partitionedPile.push(new Pile(new LinkedList(tiles.subList(indexes[a], indexes[a+1]))));
        }

        return partitionedPile;
    }

    private boolean roomToMove(int indexes[]) {
        for (int a = indexes.length - 2; a > 0; a--) {
            if (indexes[a] != indexes[a+1] - 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pile pile = (Pile) o;

        return tiles != null ? tiles.equals(pile.tiles) : pile.tiles == null;

    }

    @Override
    public int hashCode() {
        return tiles != null ? tiles.hashCode() : 0;
    }
}
