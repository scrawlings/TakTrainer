package taktrainer.bot;

import java.util.Arrays;

public class Slide implements Move {
    final int from;
    final int offset;
    final int[] partition;

    public Slide(int from, int offset, int[] partition) {
        this.from = from;
        this.offset = offset;
        this.partition = partition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Slide slide = (Slide) o;

        if (from != slide.from) return false;
        if (offset != slide.offset) return false;
        return Arrays.equals(partition, slide.partition);

    }

    @Override
    public int hashCode() {
        int result = from;
        result = 31 * result + offset;
        result = 31 * result + Arrays.hashCode(partition);
        return result;
    }
}
