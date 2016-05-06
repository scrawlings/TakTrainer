package taktrainer.bot;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static taktrainer.bot.PTN.toPTN;

public class PTNTest {
    @Test
    public void testFlatPlace() throws Exception {
        final Board b = new Board(6);
        final Place p = new Place(b.cell(4, 5), Board.F);
        assertThat(toPTN(b, p)).isEqualTo("d2");
    }

    @Test
    public void testStandingPlace() throws Exception {
        final Board b = new Board(6);
        final Place p = new Place(b.cell(4, 5), Board.S);
        assertThat(toPTN(b, p)).isEqualTo("d2S");
    }

    @Test
    public void testCapPlace() throws Exception {
        final Board b = new Board(6);
        final Place p = new Place(b.cell(4, 5), Board.C);
        assertThat(toPTN(b, p)).isEqualTo("d2C");
    }
}