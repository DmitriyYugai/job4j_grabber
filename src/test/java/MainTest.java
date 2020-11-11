import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void whenPrnt() {
        assertEquals(Main.prnt("Hello"), 1);
    }
}