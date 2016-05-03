package taktrainer.bot;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class PartitionsTest {
    @Test
    public void testPregenerateLists() throws Exception {
        final Partitions partitions = Partitions.partitions(8);

        final int[][] partitions_size5_parts2 = partitions.partitions(5, 2);
        final int[][] expected_size5_parts2 = {{1, 4}, {2, 3}, {3, 2}, {4, 1}};
        assertThat(partitions_size5_parts2).isEqualTo(expected_size5_parts2);


        final int[][] partitions_size8_parts1 = partitions.partitions(8, 1);
        final int[][] expected_size8_parts1 = {{8}};
        assertThat(partitions_size8_parts1).isEqualTo(expected_size8_parts1);


        final int[][] partitions_size7_parts7 = partitions.partitions(7, 7);
        final int[][] expected_size7_parts7 = {{1, 1, 1, 1, 1, 1, 1}};
        assertThat(partitions_size7_parts7).isEqualTo(expected_size7_parts7);
    }
}