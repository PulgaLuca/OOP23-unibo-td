package it.unibo.model.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * Test {@link GameMapFactory}.
 */
public class TestGameMapFactory {
    private static final String TEST_MAP = "test";
    private static final int TEST_MAP_ROWS = 5;
    private static final int TEST_MAP_COLUMNS = 5;

    @Test
    void testSimple() throws IOException {
        final GameMapFactory factory = new GameMapFactoryImpl();
        GameMap map = factory.fromName(TEST_MAP);
        assertEquals(map.getRows(), TEST_MAP_ROWS);
        assertEquals(map.getColumns(), TEST_MAP_COLUMNS);
    }
}
