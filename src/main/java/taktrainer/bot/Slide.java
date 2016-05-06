package taktrainer.bot;

public class Slide implements Move {
    final int fromCell;
    final int offset;
    final int[] partition;

    public Slide(int fromCell, int offset, int[] partition) {
        this.fromCell = fromCell;
        this.offset = offset;
        this.partition = partition;
    }
}
