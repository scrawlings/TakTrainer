package taktrainer;

import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class MainTest {
    @Test
    public void testNameA() throws Exception {
        assertThat("abc").isEqualToIgnoringCase("ABC");
    }

    @Ignore
    @Test
    public void testNameB() throws Exception {
        assertThat("abc").isEqualTo("def");
    }
}