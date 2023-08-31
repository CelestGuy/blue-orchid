import dev.theoduval.orchid.Map;
import dev.theoduval.orchid.io.MapIO;
import org.junit.Test;

import java.io.File;

public class MapTest {
    @Test
    public void testMap() {
        File boMap = new File("test.bomap");

        try {
            Map map = MapIO.readMap(boMap);

            assert map.getSectors().size() == 2;
        } catch (Exception ignored) {}
    }
}
