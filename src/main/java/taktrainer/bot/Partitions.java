package taktrainer.bot;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class Partitions {
    private final int size;
    private final int[][][][] pregenerated;

    private Partitions(int size, int[][][][] pregenerated) {
        this.size = size;
        this.pregenerated = pregenerated;
    }

    public static Partitions partitions(final int size) {

        for (int a = 0; a < size; a++) {
            pregenerate(a+1);
        }

        return new Partitions(size, null);
    }

    private static List<List<Integer>> pregenerate(final int size) {
        if (size == 1) {
            return asList(asList(1));
        }
        else {
            return pregenerate(size - 1).stream()
                    .map(Partitions::expand)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
    }

    private static List<List<Integer>> expand(final List<Integer> partition) {
        final ArrayList<Integer> list1 = new ArrayList<>(partition);
        list1.add(1);

        final ArrayList<Integer> list2 = new ArrayList<>(partition);
        final Integer last = list2.remove(list2.size() - 1);
        list2.add(last + 1);

        return asList(list1, list2);
    }

    public int[][] partitions(final int depth, final int chunks) {
        return pregenerated[chunks][depth];
    }
}
