package taktrainer.bot;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class Partitions {
    private final int[][][][] pregenerated; // [stackHeight][chunksInPartition][partitions][chunks]

    private Partitions(int[][][][] pregenerated) {
        this.pregenerated = pregenerated;
    }

    public static Partitions partitions(final int maxStackHeight) {
        int pregeneratedPartitions[][][][] = new int[maxStackHeight][maxStackHeight][][];

        for (int sh = 0; sh < maxStackHeight; sh++) {
            final int stackHeight = sh;

            pregenerate(stackHeight + 1).stream()
                    .collect(Collectors.groupingBy(p -> p.size()))
                    .entrySet()
                    .forEach(e -> flatten(pregeneratedPartitions[stackHeight], e.getKey(), e.getValue()));

        }

        return new Partitions(pregeneratedPartitions);
    }

    private static void flatten(final int ints[][][], final int chunksInPartition, List<List<Integer>> partitionsForChunkSize) {
        ints[chunksInPartition - 1] = new int[partitionsForChunkSize.size()][];
        final int bucket[][] = ints[chunksInPartition - 1];

        int a = 0;
        for (List<Integer> partsList : partitionsForChunkSize) {
            int partition[] = new int[partsList.size()];
            int b = 0;
            for (Integer part : partsList) {
                partition[b] = part;
                b += 1;
            }
            bucket[a] = partition;
            a += 1;
        }
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
        return pregenerated[depth - 1][chunks - 1];
    }
}
