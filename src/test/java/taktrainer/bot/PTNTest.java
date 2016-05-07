package taktrainer.bot;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static taktrainer.bot.PTN.loadPTN;
import static taktrainer.bot.PTN.toPTN;

public class PTNTest {
    @Test
    public void testFlatPlace() throws Exception {
        final Board b = new Board(6);
        final Place p = new Place(b.cell(4, 5), Board.F);
        assertThat(toPTN(b, p)).isEqualTo("d2");
        assertThat(loadPTN("d2", b)).isEqualTo(p);
    }

    @Test
    public void testStandingPlace() throws Exception {
        final Board b = new Board(6);
        final Place p = new Place(b.cell(4, 5), Board.S);
        assertThat(toPTN(b, p)).isEqualTo("Sd2");
        assertThat(loadPTN("Sd2", b)).isEqualTo(p);
    }

    @Test
    public void testCapPlace() throws Exception {
        final Board b = new Board(6);
        final Place p = new Place(b.cell(4, 5), Board.C);
        assertThat(toPTN(b, p)).isEqualTo("Cd2");
        assertThat(loadPTN("Dd2", b)).isEqualTo(p);
    }

    @Test
    public void testSlide() throws Exception {
        final int[] p = {1, 2, 3};
        final Board b = new Board(6);
        final Slide s = new Slide(b.cell(3, 5), b.offUp, p);
        assertThat(toPTN(b, s)).isEqualTo("6c2+123");
        Move actual = loadPTN("6c2+123", b);
        assertThat(actual).isEqualTo(s);
    }

    @Test
    public void testSimplestSlide() throws Exception {
        final int[] p = {1};
        final Board b = new Board(6);
        final Slide s = new Slide(b.cell(3, 5), b.offLeft, p);
        assertThat(toPTN(b, s)).isEqualTo("c2<");
        assertThat(loadPTN("c2<", b)).isEqualTo(s);
    }

    @Test
    public void testStandardSaysDoNotShowSinglePartionOnSlide() throws Exception {
        final int[] p = {3};
        final Board b = new Board(6);
        final Slide s = new Slide(b.cell(3, 5), b.offLeft, p);
        assertThat(toPTN(b, s)).isEqualTo("3c2<");
        assertThat(loadPTN("3c2<", b)).isEqualTo(s);
    }
}